package org.elsys.motorcycle_security.exceptions;

public class ServerNotRespondingException extends Error{
    public ServerNotRespondingException(String errorMessage) {
        super(errorMessage);
    }

    public ServerNotRespondingException(){
    }
}
