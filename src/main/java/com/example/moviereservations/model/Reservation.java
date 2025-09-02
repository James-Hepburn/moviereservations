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
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "showTime_id")
    private ShowTime showTime;

    @ElementCollection
    private List <String> reservedSeats = new ArrayList<>();

    private LocalDateTime reservationTime;
    private boolean canceled = false;

    public Reservation (User user, ShowTime showTime, LocalDateTime reservationTime) {
        this.user = user;
        this.showTime = showTime;
        this.reservationTime = reservationTime;
    }
}