package com.team1.qrnavigationproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static com.team1.qrnavigationproject.model.Constant.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please provide QR code description")
    @Size(min = 2, message = "Characters must be at least 2")
    private String description;

    @Column("content_id")
    private int contentId;

    @Column("space_id")
    private int spaceId;

    @Column("sub_space_id")
    private int subSpaceId;

    @Column(value = "page_url")
    @NotBlank(message = "Please provide page url")
    @Pattern(regexp = WEBSITE_URL_REGEX, message = "Invalid URL")
    private String pageURL;

    @Column("image_URL")
    @NotBlank(message = "Image URL must be provided")
    @Pattern(regexp = IMAGE_URL_REGEX, message = "Invalid image URL")
    private String imageURL;


    @Column("created_at")
    @Pattern(regexp = DATE_TIME_REGEX, message = "Invalid datetime value")
    private LocalDateTime createdAt;

}
