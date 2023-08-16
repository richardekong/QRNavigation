package com.team1.qrnavigationproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.team1.qrnavigationproject.model.Constant.IMAGE_URL_REGEX;
import static com.team1.qrnavigationproject.model.Constant.WEBSITE_URL_REGEX;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @NotNull
    private String phone;

    @Column(name = "logo_url")
    @NotBlank(message = "Logo URL must be provided")
    @Pattern(regexp = IMAGE_URL_REGEX)
    private String logoURL;

    @Column(name = "website_url")
    @NotBlank(message = "Website address must be provided")
    @Pattern(regexp = WEBSITE_URL_REGEX,
            message = "Invalid website URL")
    private String websiteURL;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<Space> spaces;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizer")
    private List<Event> events;

    @OneToOne(mappedBy = "organization")
    private User user;

    @Column(name = "header_background")
    private String headerBackground;

    @Column(name = "footer_background")
    private String footerBackground;

    public void add(Space space) {
        if (spaces == null) {
            spaces = new LinkedList<>();
        }
//        if (!spaces.contains(space)) {
            spaces.add(space);
            space.setOrganization(this);
//        }
    }

    public void add(Event event) {
        if (events == null) {
            events = new LinkedList<>();
        }
//        if (!events.contains(event)) {
            events.add(event);
            event.setOrganizer(this);
//        }
    }

    public void setUser(User user) {
        if (user != null) {
            this.user = user;
            this.user.setOrganization(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization that)) return false;
        return getId() == that.getId()
                && getName().equals(that.getName())
                && getAddress().equals(that.getAddress())
                && getPhone().equals(that.getPhone())
                && getLogoURL().equals(that.getLogoURL())
                && getWebsiteURL().equals(that.getWebsiteURL())
                && getSpaces().equals(that.getSpaces())
                && getEvents().equals(that.getEvents())
                && getUser().equals(that.getUser())
                && getHeaderBackground().equals(that.getHeaderBackground())
                && getFooterBackground().equals(that.getFooterBackground());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddress(), getPhone(), getLogoURL(),
                getWebsiteURL(), getSpaces(), getEvents(), getUser(), getHeaderBackground(),
                getFooterBackground());
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", logoURL='" + logoURL + '\'' +
                ", websiteURL='" + websiteURL + '\'' +
                ", user=" + user +
                ", headerBackground='" + headerBackground + '\'' +
                ", footerBackground='" + footerBackground + '\'' +
                '}';
    }
}
