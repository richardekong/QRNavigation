package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.team1.qrnavigationproject.model.Constant.IMAGE_URL_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="subspace")
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "main_space")
    private Space space;
//
//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name="event_id")
//    private Event event;

    @Column(name="space_type_id")
    private int typeId;

    @Override
    public String toString() {
        return "SubSpace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", spaceId=" + space.getId() +
//                ", eventId=" + event.getId() +
                ", typeId=" + typeId +
                '}';
    }
}
