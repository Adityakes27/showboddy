package com.showbuddy.movie_booking.controller;

import com.showbuddy.movie_booking.model.Show;
import com.showbuddy.movie_booking.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    @Autowired
    private ShowRepository showRepository;

    @GetMapping
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    @PostMapping
    public Show addShow(@RequestBody Show show) {
        return showRepository.save(show);
    }
}