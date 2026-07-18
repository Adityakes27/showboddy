package com.showbuddy.movie_booking.repository;

import com.showbuddy.movie_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}