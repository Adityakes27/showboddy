package com.showbuddy.movie_booking.repository;

import com.showbuddy.movie_booking.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}