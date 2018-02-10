package org.elsys.motorcycle_security.business.logic.exceptions;

public class InvalidDeviceIdException extends AbstractRestException {
    public InvalidDeviceIdException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidDeviceIdException(){
    }
}
