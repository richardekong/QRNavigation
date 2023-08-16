package com.team1.qrnavigationproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @Override
    public String toString() {
        return "Space{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photoURLs='" + photoURLs + '\'' +
                ", organizationId=" + organization.getId() +
                ", addressId=" + addressId +
                ", typeId=" + typeId +
                ", eventId=" + event.getId() +
                '}';
    }
}
