package com.showbuddy.movie_booking.repository;

import com.showbuddy.movie_booking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}