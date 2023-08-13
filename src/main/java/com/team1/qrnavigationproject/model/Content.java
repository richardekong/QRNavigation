package com.team1.qrnavigationproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.team1.qrnavigationproject.model.Constant.IMAGE_URL_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @NotBlank(message = "Please provide content description")
    private String description;

//    @Column(value = "event_id")
//    private int eventId;
//
//    @Column(value = "space_id")
//    private int spaceId;
//
//    @Column(value = "subspace_id")
//    private int subspaceId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
    @ManyToOne
    @JoinColumn(name = "subspace_id")
    private SubSpace subSpace;


}
