package com.team1.qrnavigationproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.team1.qrnavigationproject.model.Constant.IMAGE_URL_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="sub_space")
public class SubSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="Name of this place must be provided")
    private String name;

    @NotBlank(message="Description of this place must be provided")
    private String description;

    @NotBlank(message = "Photo url must not be blank")
    @Pattern(regexp=IMAGE_URL_REGEX)
    @Column(name="photo_url")
    private String photoURL;

    @ManyToOne
    @JoinColumn(name = "main_space")
    private Space space;

    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

    @Column(name="type")
    private int typeId;
}