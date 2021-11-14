package org.ifrs.enums;

public enum ErrorsEnum {
    ANNOUNCEMENT_NOT_FOUND("Anúncio não encontrado"),
    USER_NOT_FOUND("Usuário não encontrado"),
    INTEREST_BAD_REQUEST("Usuário não pode solicitar interesse no seu proprio anúncio");

    private String error;

    ErrorsEnum(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
