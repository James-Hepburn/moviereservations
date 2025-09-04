package com.example.moviereservations.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private LocalDateTime startTime;
    private int totalSeats;
    private int availableSeats;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL)
    private List <Reservation> reservations = new ArrayList<>();

    public ShowTime (Movie movie, LocalDateTime startTime, int totalSeats, int availableSeats) {
        this.movie = movie;
        this.startTime = startTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }
}