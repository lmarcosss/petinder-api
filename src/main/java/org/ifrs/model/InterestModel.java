package org.ifrs.model;

import javax.validation.constraints.NotNull;

public class InterestModel {
    public Long interestedId;
    
    @NotNull(message = "Campo announcementId é obrigatório")
    public Long announcementId;
}
