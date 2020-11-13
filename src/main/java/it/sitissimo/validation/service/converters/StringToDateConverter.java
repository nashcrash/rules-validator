package it.sitissimo.validation.service.converters;

import it.sitissimo.validation.web.rest.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component(GenericConverter.Names.stringToDate)
public class StringToDateConverter implements GenericConverter{
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSZ");

    @Override
    public Object convert(Object evaluate) throws ValidationException {
        if (evaluate instanceof String) {
            try {
                return SIMPLE_DATE_FORMAT.parse((String) evaluate);
            } catch (ParseException e) {
                throw new ValidationException(e);
            }
        }
        return null;
    }
}
