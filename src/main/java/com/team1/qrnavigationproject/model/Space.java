package com.team1.qrnavigationproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name of space must be provided")
    private String name;

    @NotBlank(message = "Description of space must be provided")
    private String description;

//    @NotBlank(message = "Photo url must not be blank")
//    @Column(value = "photo_url")
//    @Pattern(regexp = Constant.IMAGE_URL_REGEX,
//            message = "Invalid photo path")
    @javax.persistence.Column(columnDefinition = "json")
    private String photoURLs;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Column("address_id")
    private int addressId;

    @Column("type")
    private int typeId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "space",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<SubSpace> subSpaces;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="event_id")
    private Event event;

    

    public void add(SubSpace subSpace) {
        if (subSpaces == null) {
            subSpaces = new LinkedList<>();
        }
        if (!subSpaces.contains(subSpace)) {
            subSpaces.add(subSpace);
            subSpace.setSpace(this);
        }
    }

}
