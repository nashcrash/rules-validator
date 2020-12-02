package it.sitissimo.validation.br;

import it.sitissimo.validation.service.RvRuleService;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class ExpressionLanguageServicesBR {
    @Autowired
    private RvRuleService rvRuleService;

    public RvRuleDTO rule(String ruleName) {
        return rvRuleService.findOne(ruleName).orElse(null);
    }

    /**
     * Restituisce il valore della sottostringa specificata
     *
     * @return String
     */
    public String substr(String value, String start, String end) {
        if (value == null) value = "";
        if (start == null) start = "0";
        if (end == null) end = String.valueOf(value.length() - 1);

        String risultato = value.substring(Integer.parseInt(start), Integer.parseInt(end));
        return (risultato == null) ? "" : risultato;
    }

    /**
     * Restituisce la data attuale di sistema
     *
     * @return Date
     */
    public Date now() {
        return new Date();
    }

    /**
     * Restituisce la data dall'ISO.
     * <p>
     * P.e. : date(2010-01-01T00:00:00.000Z) restituisce la data 01/01/2010
     *
     * @return Date
     */
    public Date date(String isodate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(isodate);
        } catch (ParseException e) {
            throw new ValidationException(e);
        }

        return parse;
    }

    /**
     * Restituisce la data attuale modificata di gg giorni,
     * mm mesi e gg anni.
     * <p>
     * P.e. : nowadd(0,0,0) restituisce la data attuale (come now())
     * nowadd(0,0,-18) restituisce la data di 18 anni fa.
     *
     * @return String
     */
    public Date nowadd(String gg, String mm, String aaaa) {
        if (gg == null) gg = "0";
        if (mm == null) mm = "0";
        if (aaaa == null) aaaa = "0";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, Integer.parseInt(gg));
        calendar.add(Calendar.MONTH, Integer.parseInt(mm));
        calendar.add(Calendar.YEAR, Integer.parseInt(aaaa));

        return calendar.getTime();
    }
}
