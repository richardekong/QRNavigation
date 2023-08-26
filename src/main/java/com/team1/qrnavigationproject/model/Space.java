package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name of space must be provided")
    private String name;
    @NotBlank(message = "Description of space must be provided")
    private String description;

    @Column(name = "photo_urls", columnDefinition = "json")
    private String photoURLs;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Column(name = "address_id")
    private int addressId;

    @Column(name = "type")
    private int typeId;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "space",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<SubSpace> subSpaces;

    public void add(SubSpace subSpace) {
        if (subSpaces == null) {
            subSpaces = new LinkedList<>();
        }
        subSpaces.add(subSpace);
        subSpace.setSpace(this);
    }

    @Override
    public String toString() {
        return "Space{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photoURLs='" + photoURLs + '\'' +
                ", addressId=" + addressId +
                ", typeId=" + typeId +
                ", subSpaces=" + subSpaces +
                '}';
    }
}

