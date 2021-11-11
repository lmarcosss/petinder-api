package org.ifrs.model;

import javax.validation.constraints.NotNull;

public class interestModel {
    @NotNull(message = "Campo interestedId é obrigatório")
    public Long interestedId;
    
    @NotNull(message = "Campo announcementId é obrigatório")
    public Long announcementId;
}
