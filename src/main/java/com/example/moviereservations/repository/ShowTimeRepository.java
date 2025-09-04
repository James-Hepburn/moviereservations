package com.example.moviereservations.repository;

import com.example.moviereservations.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository <ShowTime, Long> {
    List <ShowTime> findByMovieId (Long movieId);
    List <ShowTime> findByStartTimeBetween (LocalDateTime start, LocalDateTime end);
}