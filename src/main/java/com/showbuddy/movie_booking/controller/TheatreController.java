package com.showbuddy.movie_booking.controller;

import com.showbuddy.movie_booking.model.Theatre;
import com.showbuddy.movie_booking.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    @Autowired
    private TheatreRepository theatreRepository;

    @GetMapping
    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    @PostMapping
    public Theatre addTheatre(@RequestBody Theatre theatre) {
        return theatreRepository.save(theatre);
    }
}