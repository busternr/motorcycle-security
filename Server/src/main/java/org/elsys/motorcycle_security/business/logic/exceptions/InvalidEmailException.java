package org.elsys.motorcycle_security.business.logic.exceptions;

public class InvalidEmailException extends AbstractRestException{
    public InvalidEmailException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidEmailException(){}
}
