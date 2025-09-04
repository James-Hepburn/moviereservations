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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin";
    }

    @GetMapping("/movies/new")
    public String newMovie(Model model) {
        model.addAttribute("movie", new Movie());
        return "admin-movie-form";
    }

    @PostMapping("/movies")
    public String createMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/admin";
    }

    @GetMapping("/movies/{id}/edit")
    public String editMovie(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        model.addAttribute("movie", movie);
        return "admin-movie-form";
    }

    @PostMapping("/movies/{id}/update")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        movieService.updateMovie(id, movie);
        return "redirect:/admin";
    }

    @PostMapping("/movies/{id}/delete")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/admin";
    }

    @GetMapping("/showtimes/new")
    public String newShowTime(@RequestParam Long movieId, Model model) {
        model.addAttribute("movieId", movieId);
        model.addAttribute("showTime", new ShowTime());
        return "admin-showtime-form";
    }

    @PostMapping("/showtimes")
    public String createShowTime(@RequestParam Long movieId, @ModelAttribute ShowTime showTime) {
        movieService.addShowTime(movieId, showTime);
        return "redirect:/admin";
    }
}