package org.elsys.motorcycle_security.business.logic.exceptions;

public class InvalidUserIdException extends AbstractRestException{
    public InvalidUserIdException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidUserIdException(){}
}
