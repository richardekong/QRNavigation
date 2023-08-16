package com.team1.qrnavigationproject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Role(String name) {
        this.name = name;
    }

    public enum RoleType{
        SUPER_ADMIN("SUPER_ADMIN"),
        ADMIN("ADMIN"),
        USER("USER");

        private final String role;
        RoleType(String role){
            this.role = role;
        }

    }
}
