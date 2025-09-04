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

@Service
public class ShowTimeService {

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    public ShowTime addShowTime(Long movieId, ShowTime showTime) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        showTime.setMovie(movie);
        return showTimeRepository.save(showTime);
    }

    public ShowTime updateShowTime(Long id, ShowTime updatedShowTime) {
        ShowTime showTime = showTimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));
        showTime.setStartTime(updatedShowTime.getStartTime());
        showTime.setTotalSeats(updatedShowTime.getTotalSeats());
        showTime.setAvailableSeats(updatedShowTime.getAvailableSeats());
        return showTimeRepository.save(showTime);
    }

    public void deleteShowTime(Long id) {
        ShowTime showTime = showTimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));
        showTimeRepository.delete(showTime);
    }

    public ShowTime getShowTimeById(Long id) {
        return showTimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));
    }

    public List<ShowTime> getShowTimesForMovie(Long movieId) {
        return showTimeRepository.findByMovieId(movieId);
    }

    public List<ShowTime> getShowTimesByDate(LocalDate date) {
        return showTimeRepository.findByStartTimeBetween(
                date.atStartOfDay(), date.atTime(LocalTime.MAX));
    }

    public List<ShowTime> getAllShowTimes() {
        return showTimeRepository.findAll();
    }
}