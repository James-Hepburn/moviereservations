package com.example.moviereservations.controller;

import com.example.moviereservations.model.Reservation;
import com.example.moviereservations.model.ShowTime;
import com.example.moviereservations.model.User;
import com.example.moviereservations.service.ReservationService;
import com.example.moviereservations.service.ShowTimeService;
import com.example.moviereservations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping
    public String listReservations(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<Reservation> reservations = reservationService.getReservationsByUser(user.getId());
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    @PostMapping("/create")
    public String createReservation(@RequestParam Long showTimeId,
                                    @RequestParam int numSeats,
                                    Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        ShowTime showTime = showTimeService.getShowTimeById(showTimeId);

        if (numSeats > showTime.getAvailableSeats()) {
            throw new RuntimeException("Not enough available seats");
        }

        List<String> assignedSeats = IntStream.range(1, numSeats + 1)
                .mapToObj(i -> "Seat " + i)
                .toList();

        reservationService.createReservation(user.getId(), showTimeId, assignedSeats);
        return "redirect:/reservations";
    }

    @PostMapping("/{id}/cancel")
    public String cancelReservation(@PathVariable Long id, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        reservationService.cancelReservation(id, user.getId());
        return "redirect:/reservations";
    }
}