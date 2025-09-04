package com.example.moviereservations.service;

import com.example.moviereservations.model.Movie;
import com.example.moviereservations.model.ShowTime;
import com.example.moviereservations.repository.MovieRepository;
import com.example.moviereservations.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    public Movie addMovie (Movie movie) {
        return this.movieRepository.save (movie);
    }

    public Movie updateMovie (Long id, Movie updatedMovie) {
        Movie movie = this.movieRepository.findById (id)
                .orElseThrow (() -> new RuntimeException ("Movie not found"));

        movie.setDescription (updatedMovie.getDescription ());
        movie.setGenre (updatedMovie.getGenre ());
        movie.setTitle (updatedMovie.getTitle ());
        movie.setShowTimes (updatedMovie.getShowTimes ());
        movie.setPosterImageUrl (updatedMovie.getPosterImageUrl ());

        return this.movieRepository.save (movie);
    }

    public void deleteMovie (Long id) {
        Movie movie = this.movieRepository.findById (id)
                .orElseThrow (() -> new RuntimeException ("Movie not found"));
        this.movieRepository.delete (movie);
    }

    public List <Movie> getAllMovies () {
        return this.movieRepository.findAll ();
    }

    public Optional <Movie> getMovieById (Long id) {
        return this.movieRepository.findById (id);
    }

    public List <Movie> getMoviesByGenre (String genre) {
        return this.movieRepository.findByGenre (genre);
    }

    public ShowTime addShowTime (Long movieId, ShowTime showTime) {
        Movie movie = this.movieRepository.findById (movieId)
                .orElseThrow (() -> new RuntimeException ("Movie not found"));
        movie.getShowTimes ().add (showTime);
        this.movieRepository.save (movie);
        return this.showTimeRepository.save (showTime);
    }

    public List <ShowTime> getShowTimesByMovie (Long movieId) {
        return this.showTimeRepository.findByMovieId (movieId);
    }

    public List <ShowTime> getShowTimesByDate (LocalDate date) {
        return this.showTimeRepository.findByStartTimeBetween (date.atStartOfDay (), date.atTime (LocalTime.MAX));
    }
}