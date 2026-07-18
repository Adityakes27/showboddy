package com.showbuddy.movie_booking.controller;

import com.showbuddy.movie_booking.model.Favorite;
import com.showbuddy.movie_booking.model.Movie;
import com.showbuddy.movie_booking.model.User;
import com.showbuddy.movie_booking.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    // User ki saari favorite movies dikhao
    @GetMapping("/{userId}")
    public List<Favorite> getFavorites(@PathVariable Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    // Favorite add/remove karo (toggle)
    @PostMapping("/toggle")
    public Map<String, Object> toggleFavorite(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        Long movieId = body.get("movieId");

        Optional<Favorite> existing = favoriteRepository.findByUserIdAndMovieId(userId, movieId);
        Map<String, Object> response = new HashMap<>();

        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            response.put("favorited", false);
        } else {
            Favorite favorite = new Favorite();
            User user = new User();
            user.setId(userId);
            Movie movie = new Movie();
            movie.setId(movieId);
            favorite.setUser(user);
            favorite.setMovie(movie);
            favoriteRepository.save(favorite);
            response.put("favorited", true);
        }
        return response;
    }
}