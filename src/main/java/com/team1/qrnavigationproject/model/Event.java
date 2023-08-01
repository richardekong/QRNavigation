package com.team1.qrnavigationproject.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static com.team1.qrnavigationproject.model.Constant.DATE_TIME_REGEX;

@NoArgsConstructor
@AllArgsConstructor
//@Data
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

    @ManyToOne
    @JoinColumn(name="organizer")
    private Organization organizer;

    @Column(value = "space_id")
    private int spaceId;

    @NotNull(message = "Please provide a start date and time")
    @Pattern(regexp = DATE_TIME_REGEX,
            message = "Invalid datetime value")
    @Future(message = "Start date and time must be latter than now")
    private LocalDateTime start;

    @NotNull(message = "Please provide an end date and time")
    @Pattern(regexp = DATE_TIME_REGEX,
            message = "Invalid datetime value")
    private LocalDateTime end;

}
