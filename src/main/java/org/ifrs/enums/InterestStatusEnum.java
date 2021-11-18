package org.ifrs.enums;

public enum InterestStatusEnum {
    OPENNED("ABERTO"),
    ACCEPTED("ACEITO"),
    DECLINED("NÃO ACEITO");

    private String status;

    InterestStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
