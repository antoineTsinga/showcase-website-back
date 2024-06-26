package org.onyx.showcasebackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private String entity;

    private Boolean constrained;


    @ManyToOne
    @JoinColumn(name="role_id")
    @JsonIgnore
    private Role role;

    public Privilege() {

    }

    public Privilege(String action, String entity, Boolean constrained, Role role) {
        this.action = action;
        this.entity = entity;
        this.constrained = constrained;
        this.role = role;
    }

    public Boolean isConstrained(){
        return constrained;
    }

    public void setIsConstrained(Boolean isConstrained){
        constrained = isConstrained;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
