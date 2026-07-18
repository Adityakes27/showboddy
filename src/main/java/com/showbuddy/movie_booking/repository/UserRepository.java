package com.showbuddy.movie_booking.repository;

import com.showbuddy.movie_booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}