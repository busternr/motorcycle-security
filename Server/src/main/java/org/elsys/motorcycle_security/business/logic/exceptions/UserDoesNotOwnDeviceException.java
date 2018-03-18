package org.elsys.motorcycle_security.business.logic.exceptions;

public class UserDoesNotOwnDeviceException extends AbstractRestException {
    public UserDoesNotOwnDeviceException(String errorMessage) {
        super(errorMessage);
    }

    public UserDoesNotOwnDeviceException(){
    }
}
