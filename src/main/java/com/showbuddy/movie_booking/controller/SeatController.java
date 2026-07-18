package com.showbuddy.movie_booking.controller;

import com.showbuddy.movie_booking.model.Seat;
import com.showbuddy.movie_booking.model.Show;
import com.showbuddy.movie_booking.repository.SeatRepository;
import com.showbuddy.movie_booking.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    // Ek show ki saari seats dikhao
    @GetMapping("/show/{showId}")
    public List<Seat> getSeatsForShow(@PathVariable Long showId) {
        return seatRepository.findByShowId(showId);
    }

    // Ek show ke liye seats generate karo (admin use karega, ek baar per show)
    @PostMapping("/generate/{showId}")
    public String generateSeats(@PathVariable Long showId) {
        Optional<Show> showOpt = showRepository.findById(showId);
        if (showOpt.isEmpty()) {
            return "Show not found";
        }
        Show show = showOpt.get();

        // Agar already seats bani hain to dobara na banao
        List<Seat> existing = seatRepository.findByShowId(showId);
        if (!existing.isEmpty()) {
            return "Seats already exist for this show";
        }

        String[] rows = {"A", "B", "C", "D", "E"};
        for (String row : rows) {
            for (int i = 1; i <= 10; i++) {
                Seat seat = new Seat();
                seat.setSeatNumber(row + i);
                seat.setIsBooked(false);
                seat.setShow(show);
                seatRepository.save(seat);
            }
        }
        return "50 seats generated for show " + showId;
    }

    // Ek seat ko booked mark karo
    @PutMapping("/book/{seatId}")
    public Seat bookSeat(@PathVariable Long seatId) {
        Optional<Seat> seatOpt = seatRepository.findById(seatId);
        if (seatOpt.isEmpty()) {
            throw new RuntimeException("Seat not found");
        }
        Seat seat = seatOpt.get();
        seat.setIsBooked(true);
        return seatRepository.save(seat);
    }
}