package com.example.moviereservations.controller;

import com.example.moviereservations.model.Movie;
import com.example.moviereservations.model.ShowTime;
import com.example.moviereservations.service.MovieService;
import com.example.moviereservations.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies";
    }

    @GetMapping("/{id}")
    public String showBooking(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        List<ShowTime> showTimes = showTimeService.getShowTimesForMovie(id);
        model.addAttribute("movie", movie);
        model.addAttribute("showTimes", showTimes);
        return "booking";
    }
}