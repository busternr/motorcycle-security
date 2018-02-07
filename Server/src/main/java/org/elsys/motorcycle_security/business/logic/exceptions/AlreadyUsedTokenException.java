package org.elsys.motorcycle_security.business.logic.exceptions;

public class AlreadyUsedTokenException extends AbstractRestException {
    public AlreadyUsedTokenException(String errorMessage) {
        super(errorMessage);
    }

    public AlreadyUsedTokenException(){}
}
