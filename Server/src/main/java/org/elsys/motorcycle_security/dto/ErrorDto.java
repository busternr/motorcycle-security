package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.business.logic.exceptions.AbstractRestException;

public class ErrorDto {
    private String message;

    public ErrorDto(String message){
        this.message = message;
    }

    public ErrorDto(AbstractRestException e){
        this.message=e.getMessage();
    }

    public ErrorDto(Exception e) {
        this.message=e.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}