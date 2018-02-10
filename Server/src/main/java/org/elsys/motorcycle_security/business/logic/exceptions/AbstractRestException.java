package org.elsys.motorcycle_security.business.logic.exceptions;

public abstract class AbstractRestException extends Error {
    public AbstractRestException(String errorMessage){
        super(errorMessage);
    }

    public AbstractRestException(){
    }
}
