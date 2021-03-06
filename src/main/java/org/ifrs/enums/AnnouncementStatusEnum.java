package org.ifrs.enums;

public enum AnnouncementStatusEnum {
    OPENNED("ABERTO"),
    CANCELED("CANCELADO"),
    ADOPTED("ADOTADO");

    private String status;

    AnnouncementStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
