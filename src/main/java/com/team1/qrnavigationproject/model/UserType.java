package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="user_type")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="User must be categorized as either an adult, child, student or staff")
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public UserType(String name){
        this.name = name;
    }

}

