package org.ifrs.enums;

public enum ErrorsEnum {
    ANNOUNCEMENT_NOT_FOUND("Anúncio não encontrado"),
    USER_NOT_FOUND("Usuário não encontrado"),
    INTEREST_ANNOUNCEMENT_BAD_REQUEST("Usuário não pode solicitar interesse no seu proprio anúncio"),
    INTEREST_NOT_FOUND("Solicitação de interesse não encontrada"),
    INTEREST_STATUS_BAD_REQUEST("Solicitação já foi finalizada"),
    INTEREST_DUPLICATION_BAD_REQUEST("Solicitação para o anúncio já foi criada pelo usuário"),
    UNAUTHORIZED_USER_ANNOUNCEMENT("Somente o dono do anúncio pode manipulá-lo");

    private String error;

    ErrorsEnum(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
