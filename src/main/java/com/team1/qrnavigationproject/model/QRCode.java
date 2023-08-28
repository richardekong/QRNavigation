package com.team1.qrnavigationproject.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static com.team1.qrnavigationproject.model.Constant.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please provide QR code description")
    @Size(min = 2, message = "Characters must be at least 2")
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="space_id")
    private Space space;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sub_space_id")
    private SubSpace subSpace;

    @Column(name = "page_url")
    @NotBlank(message = "Please provide page url")
    @Pattern(regexp = WEBSITE_URL_REGEX, message = "Invalid URL")
    private String pageURL;

    @Column(name="image_URL")
    @NotBlank(message = "Image URL must be provided")
    @Pattern(regexp = IMAGE_URL_REGEX, message = "Invalid image URL")
    private String imageURL;

    @Column(name="created_at")
//    @Pattern(regexp = DATE_TIME_REGEX, message = "Invalid datetime value")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "QRCode{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", space=" + space +
                ", subSpace=" + subSpace +
                ", pageURL='" + pageURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
