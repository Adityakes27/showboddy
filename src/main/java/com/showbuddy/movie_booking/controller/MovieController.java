package com.showbuddy.movie_booking.controller;

import com.showbuddy.movie_booking.model.Movie;
import com.showbuddy.movie_booking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    // Get all movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Add a new movie
    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }
     @PutMapping("/{id}")
 public Movie updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
     updatedMovie.setId(id);
     return movieRepository.save(updatedMovie);
 }

}