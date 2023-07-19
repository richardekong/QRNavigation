package com.example.qrnavigationproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.qrnavigationproject.model.Constant.POSTCODE_REGEX;

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

    private int locationId;

    @NotBlank(message ="Please provide a postcode")
    @Pattern(regexp = POSTCODE_REGEX, message = "Invalid UK postcode")
    private String postcode;
}
