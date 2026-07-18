package com.showbuddy.movie_booking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "theatres")
@Data
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String city;

    private String address;

    private Integer totalSeats;
}