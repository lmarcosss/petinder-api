package org.ifrs.entity;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class Error implements ExceptionMapper<ClientErrorException> {
    private String message;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Response toResponse(ClientErrorException exception) {
        this.status = exception.getResponse().getStatus();
        this.message = exception.getMessage();

        return Response.status(this.status).entity(this).build();
    }
}
