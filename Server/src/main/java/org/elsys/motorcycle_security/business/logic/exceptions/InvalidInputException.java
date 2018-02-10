package org.elsys.motorcycle_security.business.logic.exceptions;

public class InvalidInputException extends AbstractRestException {
    public InvalidInputException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidInputException(){
    }
}
