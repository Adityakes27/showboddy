package com.showbuddy.movie_booking.controller;

import com.showbuddy.movie_booking.model.Movie;
import com.showbuddy.movie_booking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/omdb")
public class OmdbController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @Value("${omdb.api.key}")
    private String omdbApiKey;

    // Ek specific movie ka naam de kar OMDb se fetch karo aur save karo
    @PostMapping("/fetch")
    public String fetchMovie(@RequestParam String title) {
        String url = "https://www.omdbapi.com/?apikey=" + omdbApiKey + "&t=" + title;

        Map<String, Object> data = restTemplate.getForObject(url, Map.class);

        if (data == null || "False".equals(data.get("Response"))) {
            return "Movie not found: " + title;
        }

        boolean exists = movieRepository.findAll().stream()
                .anyMatch(m -> m.getTitle().equalsIgnoreCase((String) data.get("Title")));
        if (exists) {
            return "Movie already exists: " + data.get("Title");
        }

        Movie movie = new Movie();
        movie.setTitle((String) data.get("Title"));
        movie.setDescription((String) data.get("Plot"));
        movie.setGenre((String) data.get("Genre"));
        movie.setLanguage((String) data.get("Language"));
        movie.setReleaseDate((String) data.get("Released"));

        String runtime = (String) data.get("Runtime"); // e.g. "128 min"
        if (runtime != null && runtime.contains(" ")) {
            try {
                movie.setDurationMinutes(Integer.parseInt(runtime.split(" ")[0]));
            } catch (Exception e) {
                movie.setDurationMinutes(120);
            }
        }

        String poster = (String) data.get("Poster");
        if (poster != null && !poster.equals("N/A")) {
            movie.setPosterUrl(poster);
        }

        movieRepository.save(movie);
        return "Movie added: " + movie.getTitle();
    }
}