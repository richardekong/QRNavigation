package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.Objects;

import static com.team1.qrnavigationproject.model.Constant.POSTCODE_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please provide a description")
    private String description;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @NotBlank(message ="Please provide a postcode")
    @Pattern(regexp = POSTCODE_REGEX, message = "Invalid UK postcode")
    private String postcode;

    @JsonManagedReference
    @OneToOne(mappedBy = "address",fetch = FetchType.LAZY ,cascade = CascadeType.PERSIST)
    private Organization organization;

    public void setOrganization(Organization organization) {
        if (organization != null){
            this.organization = organization;
            this.organization.setAddress(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return getId() == address.getId()
                && getDescription().equals(address.getDescription())
                && getLocation().equals(address.getLocation())
                && getPostcode().equals(address.getPostcode())
                && getOrganization().equals(address.getOrganization());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getLocation(), getPostcode(), getOrganization());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", description='" + description + '\'' +
//                ", location=" + location +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
