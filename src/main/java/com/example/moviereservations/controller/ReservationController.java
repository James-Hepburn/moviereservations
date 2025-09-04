package com.example.moviereservations.controller;

import com.example.moviereservations.model.Reservation;
import com.example.moviereservations.model.User;
import com.example.moviereservations.service.ReservationService;
import com.example.moviereservations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listReservations(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<Reservation> reservations = reservationService.getReservationsByUser(user.getId());
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    @PostMapping("/create")
    public String createReservation(@RequestParam Long showTimeId,
                                    @RequestParam List<String> seats,
                                    Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        reservationService.createReservation(user.getId(), showTimeId, seats);
        return "redirect:/reservations";
    }

    @PostMapping("/{id}/cancel")
    public String cancelReservation(@PathVariable Long id, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        reservationService.cancelReservation(id, user.getId());
        return "redirect:/reservations";
    }
}