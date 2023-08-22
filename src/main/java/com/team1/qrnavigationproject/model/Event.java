package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.team1.qrnavigationproject.model.Constant.DATE_TIME_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please provide a name")
    @Size(min = 2, message = "Characters must be at least 2")
    private String name;

    @NotBlank(message = "Please provide a name")
    @Size(min = 2, message = "Characters must be at least 2")
    private String description;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="organizer")
    private Organization organizer;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<Space> spaces;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<SubSpace> subSpaces;

    @NotNull(message = "Please provide a start date and time")
    @Pattern(regexp = DATE_TIME_REGEX,
            message = "Invalid datetime value")
    @Future(message = "Start date and time must be latter than now")
    private LocalDateTime start;

    @NotNull(message = "Please provide an end date and time")
    @Pattern(regexp = DATE_TIME_REGEX,
            message = "Invalid datetime value")
    private LocalDateTime end;

    @Column(columnDefinition = "json")
    private String imageUrls;

    public void addSpace(Space space){
        if (spaces == null){
            spaces = new ArrayList<>();
        }
//        if (!spaces.contains(space)){
            spaces.add(space);
            space.setEvent(this);
//        }
    }

    public void addSubSpace(SubSpace subSpace){
        if (subSpaces == null){
            subSpaces = new ArrayList<>();
        }

//        if (!subSpaces.contains(subSpace)){
            subSpaces.add(subSpace);
            subSpace.setEvent(this);
//        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", spaces=" + spaces +
                ", subSpaces=" + subSpaces +
                ", start=" + start +
                ", end=" + end +
                ", imageUrls='" + imageUrls + '\'' +
                '}';
    }
}
