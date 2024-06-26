package org.onyx.showcasebackend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Admin extends User{
    @Column(columnDefinition = "TEXT")
    private String description;

    public Admin() {
    }

    public Admin(String firstName, String lastName, Long tel, String avatar, String email, Role role, String password, String description) {
        super(firstName, lastName, tel, avatar, email, password,role);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
