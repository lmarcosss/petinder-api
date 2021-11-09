package org.ifrs.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.ifrs.model.UserModel;

@Entity
public class User extends BaseEntity<UserModel> {
    @Basic(optional = false)
    private String name;
    
    @Basic(optional = false)
    private String birthDay;

    @Basic(optional = false)
    private String cpf;

    @Basic(optional = false)
    private String phone;

    @Basic(optional = false)
    private String description;
    
    @Basic(optional = false)
    private String email;

    @Basic(optional = false)
    private String password;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void mapFromEntity(UserModel user) {
        this.birthDay = user.birthDay;
        this.cpf = user.cpf;
        this.description = user.description;
        this.email = user.email;
        this.name = user.name;
        this.password = user.password;
        this.phone = user.phone;        
    }
}
