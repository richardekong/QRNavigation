package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter @Setter
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double latitude;

    private double longitude;

    @JsonManagedReference
    @OneToOne(mappedBy = "location", cascade = CascadeType.REFRESH)
    private Address address;
    public void setAddress(Address address) {
        if(address!=null) {
            this.address = address;
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address=" + address +
                '}';
    }
}


