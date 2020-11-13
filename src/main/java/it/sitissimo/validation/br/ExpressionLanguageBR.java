package it.sitissimo.validation.br;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.web.rest.errors.ValidationException;
import lombok.Data;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExpressionLanguageBR {
    public Object evaluate(String evaluating, RvRuleDTO ruleDTO, JsonNode model) throws ValidationException {
        Pattern pattern=Pattern.compile("\\#\\{(.*)\\}");
        Matcher matcher=pattern.matcher(evaluating);
        while (matcher.find()) {
            String elString=matcher.group(1);
            String result=String.valueOf(el(elString, ruleDTO, model));

            String substitute="#{"+elString+"}";
            evaluating=evaluating.replaceFirst(Pattern.quote(substitute),result);
        }
        return evaluating;
    }

    protected String el(String expression, RvRuleDTO ruleDTO, JsonNode model) throws ValidationException {
        Object[] expressions= ArrayUtils.addAll(new Object[0], expression.split(" "));
        List<NodoEl> nodes=new ArrayList<NodoEl>();
        for (int i = 0; i < expressions.length; i++) {
            try {
                expressions[i]=executeFunction(String.valueOf(expressions[i]), ruleDTO, model);
            } catch(ValidationException e) {
                throw e;
            } catch(Exception e) {
                e.printStackTrace();
            }
            nodes.add(new NodoEl(String.valueOf(expressions[i])));
        }
        NodoEl root=evaluateLogicalExpression(nodes);

        String value = root.risolvi();
        return value;
    }

    private Object executeFunction(String elString, RvRuleDTO ruleDTO, JsonNode model) throws ValidationException {
        Object result=elString;
        Pattern pattern=Pattern.compile("([^()]*)\\(((.*)+)\\)");
        Matcher matcher=pattern.matcher(elString);
        if (matcher.find()) {
            String expression=matcher.group(1);
            String[] expressions=expression.split("\\.");
            Object[] parameters= ArrayUtils.addAll(new Object[0], matcher.group(2).split(","));

            Object oggetto=prepareTargetObject(model, ruleDTO, expressions, expressions.length-1);

            //Check if params is a map!
            if (parameters.length==1) {
                try {
                    result= PropertyUtils.getProperty(oggetto, expressions[expressions.length-1]+"("+parameters[0]+")");
                    return result;
                } catch (Exception e) {}
            }

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof String) {
                    parameters[i]=((String) parameters[i]).trim();
                }
                parameters[i]=executeFunction(String.valueOf(parameters[i]), ruleDTO, model);
            }

            try {
                result= MethodUtils.invokeMethod(oggetto, expressions[expressions.length-1], parameters);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof ValidationException) {
                    throw (ValidationException) e.getTargetException();
                } else {
                    throw new IllegalArgumentException(e);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            String[] expressions=elString.split("\\.");
            result=prepareTargetObject(model, ruleDTO, expressions, expressions.length);
        }
        return result;
    }

    private Object prepareTargetObject(JsonNode model, RvRuleDTO ruleDTO, String expression) throws ValidationException {
        Object oggetto=null;
        if ("rule".equals(expression)) {
            oggetto=ruleDTO;
        } else if ("model".equals(expression)) {
            oggetto=model;
        } else if ("el".equals(expression)) {
            oggetto=this;
        }
        if (oggetto==null) {
            oggetto=expression; //Caso stringa!
        }
        return oggetto;
    }

    private Object prepareTargetObject(JsonNode model, RvRuleDTO ruleDTO, String[] expressions, int length) throws ValidationException {
        Object oggetto=prepareTargetObject(model, ruleDTO, expressions[0]);

        if (oggetto==model) {
            for (int i = 1; i < length; i++) {
                if (oggetto != null) {
                    oggetto=((JsonNode) oggetto).get(expressions[i]);
                }
            }
            if (oggetto != null) {
                oggetto = ((JsonNode) oggetto).asText();
            }
        } else {
            for (int i = 1; i < length; i++) {
                try {
                    //Try to get property
                    oggetto = PropertyUtils.getProperty(oggetto, expressions[i]);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return oggetto;
    }



    private NodoEl evaluateLogicalExpression(List<NodoEl> nodes) throws ValidationException {
        NodoEl result=null;
        if (nodes.size()<=0) {
            result=new NodoEl(Boolean.FALSE.toString());
        } else if (nodes.size()<=2) {
            result= nodes.get(0);
        } else if (nodes.size()==3 && nodes.get(1).isOperatore()) {
            result= nodes.get(1);
            result.setLeft(nodes.get(0));
            result.setRight(nodes.get(2));
        } else if (nodes.get(3).isOperatore()) {
            result= nodes.get(3);
            NodoEl left=evaluateLogicalExpression(nodes.subList(0, 3));
            NodoEl right=evaluateLogicalExpression(nodes.subList(4, nodes.size()));
            result.setLeft(left);
            result.setRight(right);
        }
        return result;
    }
}

@Data
class NodoEl {
    public static final String[] OPERATORS ={"eq","ne","neq","or","and","gt","lt","ge","le"};

    private NodoEl left;
    private NodoEl right;
    private String value;

    public NodoEl(String value) {
        super();
        this.value = value;
    }
    public NodoEl(NodoEl left, String value, NodoEl right) {
        this(value);
        this.left = left;
        this.right = right;
    }

    public boolean isOperatore() {
        return isOperatore(this.value);
    }

    public static boolean isOperatore(String valore) {
        for (String operator : OPERATORS) {
            if (operator.equals(valore)) return true;
        }
        return false;
    }

    public boolean isFull() {
        return (this.left !=null && this.right !=null);
    }

    public String risolvi() {
        if ("eq".equals(this.value)) {
            return String.valueOf(this.left.risolvi().equals(this.right.risolvi()));
        } else if ("ne".equals(this.value) || "neq".equals(this.value)) {
            return String.valueOf(!this.left.risolvi().equals(this.right.risolvi()));
        } else if ("or".equals(this.value)) {
            return String.valueOf(Boolean.parseBoolean(this.left.risolvi()) || Boolean.parseBoolean(this.right.risolvi()));
        } else if ("and".equals(this.value)) {
            return String.valueOf(Boolean.parseBoolean(this.left.risolvi()) && Boolean.parseBoolean(this.right.risolvi()));
        } else if ("gt".equals(this.value)) {
            return String.valueOf(new BigDecimal(this.left.risolvi()).compareTo(new BigDecimal(this.right.risolvi())) > 0);
        } else if ("lt".equals(this.value)) {
            return String.valueOf(new BigDecimal(this.left.risolvi()).compareTo(new BigDecimal(this.right.risolvi())) < 0);
        } else if ("ge".equals(this.value)) {
            return String.valueOf(new BigDecimal(this.left.risolvi()).compareTo(new BigDecimal(this.right.risolvi())) >= 0);
        } else if ("le".equals(this.value)) {
            return String.valueOf(new BigDecimal(this.left.risolvi()).compareTo(new BigDecimal(this.right.risolvi())) <= 0);
        }
        return this.value;
    }
}
