package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import static com.team1.qrnavigationproject.model.Constant.DATE_TIME_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "event")
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
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "organizer")
    private Organization organizer;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<Venue> venues;

    public void add(Venue venue) {
        if (venues == null) {
            venues = new LinkedList<>();
        }
        if (!venues.contains(venue)) {
            venues.add(venue);
            venue.setEvent(this);
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", imageUrls='" + imageUrls + '\'' +
                ", venues=" + venues +
                '}';
    }
}


