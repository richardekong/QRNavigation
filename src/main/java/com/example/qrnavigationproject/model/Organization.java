package com.example.qrnavigationproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;

import static com.example.qrnavigationproject.model.Constant.IMAGE_URL_REGEX;
import static com.example.qrnavigationproject.model.Constant.WEBSITE_URL_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int addressId;

    @NotNull
    private String phone;

    @Column("logo_URL")
    @NotBlank(message="Logo URL must be provided")
    @Pattern(regexp =IMAGE_URL_REGEX)
    private String logoURL;

    @Column("website_URL")
    @NotBlank(message="Website address must be provided")
    @Pattern(regexp = WEBSITE_URL_REGEX,
            message = "Invalid website URL")
    private String websiteURL;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organization")
    private List<Space> spaces;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<Event> events;

    public void add(Space space){
        if (spaces==null) {
            spaces = new LinkedList<>();
        }
        if (!spaces.contains(space)) {
            spaces.add(space);
            space.setOrganization(this);
        }
    }

    public void add(Event event){
        if (events == null){
            events = new LinkedList<>();
        }
        if (!events.contains(event)){
            events.add(event);
            event.setOrganizer(this);
        }
    }

}
