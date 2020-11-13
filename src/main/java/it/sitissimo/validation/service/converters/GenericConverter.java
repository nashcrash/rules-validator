package it.sitissimo.validation.service.converters;

import it.sitissimo.validation.web.rest.errors.ValidationException;

public interface GenericConverter {
    Object convert(Object evaluate) throws ValidationException;

    interface Names {
        String stringToDate ="stringToDate";
        String dateToString ="dateToString";
    }
}
