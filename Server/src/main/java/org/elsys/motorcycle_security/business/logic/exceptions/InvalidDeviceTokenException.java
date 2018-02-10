package org.elsys.motorcycle_security.business.logic.exceptions;

public class InvalidDeviceTokenException extends AbstractRestException {
    public InvalidDeviceTokenException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidDeviceTokenException(){
    }
}
