package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne(mappedBy = "location", cascade = CascadeType.REFRESH)
    private Address address;
    public void setAddress(Address address) {
        if(address!=null) {
            this.address = address;
        }
    }

}


