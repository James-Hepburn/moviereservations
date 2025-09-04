package com.example.moviereservations.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String description;
    private String genre;
    private String posterImageUrl;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List <ShowTime> showTimes = new ArrayList<>();

    public Movie (String title, String description, String genre, String posterImageUrl) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.posterImageUrl = posterImageUrl;
    }
}