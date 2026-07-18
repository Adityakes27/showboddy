package com.showbuddy.movie_booking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movies")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String genre;

    private String language;

    private Integer durationMinutes;

    private String posterUrl;

    private String releaseDate;
}