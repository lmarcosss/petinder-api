package org.ifrs.model;

import javax.validation.constraints.NotNull;

public class UserModel {
    @NotNull(message = "Campo id é obrigatório")
    public String id;
    
    @NotNull(message = "Campo nome é obrigatório")
    public String name;

    @NotNull(message = "Campo data de nascimento é obrigatório")
    public String birthDay;

    @NotNull(message = "Campo telefone é obrigatório")
    public String phone;

    @NotNull(message = "Campo descrição é obrigatório")
    public String description;
}
