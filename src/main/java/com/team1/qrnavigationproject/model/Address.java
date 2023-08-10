package com.team1.qrnavigationproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.team1.qrnavigationproject.model.Constant.POSTCODE_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please provide a description")
    private String description;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @NotBlank(message ="Please provide a postcode")
    @Pattern(regexp = POSTCODE_REGEX, message = "Invalid UK postcode")
    private String postcode;

    @OneToOne(mappedBy = "address", cascade = CascadeType.REFRESH)
    private Organization organization;
}
