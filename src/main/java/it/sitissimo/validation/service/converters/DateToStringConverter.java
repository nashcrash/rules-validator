package it.sitissimo.validation.service.converters;

import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component(GenericConverter.Names.dateToString)
public class DateToStringConverter implements GenericConverter{
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSZ");

    @Override
    public Object convert(Object evaluate) throws ValidationException {
        if (evaluate instanceof Date) {
            return SIMPLE_DATE_FORMAT.format((Date) evaluate);
        }
        return null;
    }
}
