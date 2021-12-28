package org.ifrs.model;

import java.util.List;
import javax.validation.constraints.NotNull;

public class AnnouncementModel {
    @NotNull(message = "Campo latitude é obrigatório")
    public Double latitude;

    @NotNull(message = "Campo longitude é obrigatório")
    public Double longitude;

    @NotNull(message = "Campo title é obrigatório")
    public String title;

    @NotNull(message = "Campo description é obrigatório")
    public String description;

    @NotNull(message = "Campo pictures é obrigatório")
    public List<String> pictures;
}
