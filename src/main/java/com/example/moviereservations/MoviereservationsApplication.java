package com.example.moviereservations;

import com.example.moviereservations.model.Movie;
import com.example.moviereservations.model.ShowTime;
import java.time.LocalDateTime;
import com.example.moviereservations.repository.MovieRepository;
import com.example.moviereservations.repository.ShowTimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoviereservationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviereservationsApplication.class, args);
    }

    @Bean
    CommandLineRunner initMovies(MovieRepository movieRepository) {
        return args -> {
            if (movieRepository.findByTitle("Inception").isEmpty()) {
                movieRepository.save(new Movie(
                        "Inception",
                        "A thief who steals corporate secrets through dream-sharing technology.",
                        "Sci-Fi",
                        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_FMjpg_UX1000_.jpg"
                ));
            }
            if (movieRepository.findByTitle("The Dark Knight").isEmpty()) {
                movieRepository.save(new Movie(
                        "The Dark Knight",
                        "Batman faces the Joker, who wreaks havoc on Gotham City.",
                        "Action",
                        "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
                ));
            }
            if (movieRepository.findByTitle("Interstellar").isEmpty()) {
                movieRepository.save(new Movie(
                        "Interstellar",
                        "A team of explorers travel through a wormhole in space in an attempt to save humanity.",
                        "Sci-Fi",
                        "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"
                ));
            }
            if (movieRepository.findByTitle("The Prestige").isEmpty()) {
                movieRepository.save(new Movie(
                        "The Prestige",
                        "Two magicians engage in a rivalry to create the ultimate stage illusion.",
                        "Drama/Thriller",
                        "https://m.media-amazon.com/images/M/MV5BMTM3MzQ5MjQ5OF5BMl5BanBnXkFtZTcwMTQ3NzMzMw@@._V1_FMjpg_UX1000_.jpg"
                ));
            }
        };
    }

    @Bean
    CommandLineRunner initShowTimes(MovieRepository movieRepository, ShowTimeRepository showTimeRepository) {
        return args -> {
            LocalDateTime now = LocalDateTime.now();

            Movie inception = movieRepository.findByTitle("Inception").orElse(null);
            if (inception != null && showTimeRepository.findByMovieId(inception.getId()).isEmpty()) {
                showTimeRepository.save(new ShowTime(inception, now.plusDays(1).withHour(19).withMinute(0), 30, 30));
                showTimeRepository.save(new ShowTime(inception, now.plusDays(1).withHour(21).withMinute(0), 30, 30));
            }

            Movie darkKnight = movieRepository.findByTitle("The Dark Knight").orElse(null);
            if (darkKnight != null && showTimeRepository.findByMovieId(darkKnight.getId()).isEmpty()) {
                showTimeRepository.save(new ShowTime(darkKnight, now.plusDays(2).withHour(18).withMinute(30), 30, 30));
                showTimeRepository.save(new ShowTime(darkKnight, now.plusDays(2).withHour(21).withMinute(0), 30, 30));
            }

            Movie interstellar = movieRepository.findByTitle("Interstellar").orElse(null);
            if (interstellar != null && showTimeRepository.findByMovieId(interstellar.getId()).isEmpty()) {
                showTimeRepository.save(new ShowTime(interstellar, now.plusDays(3).withHour(17).withMinute(0), 30, 30));
                showTimeRepository.save(new ShowTime(interstellar, now.plusDays(3).withHour(20).withMinute(0), 30, 30));
            }

            Movie prestige = movieRepository.findByTitle("The Prestige").orElse(null);
            if (prestige != null && showTimeRepository.findByMovieId(prestige.getId()).isEmpty()) {
                showTimeRepository.save(new ShowTime(prestige, now.plusDays(4).withHour(19).withMinute(0), 30, 30));
                showTimeRepository.save(new ShowTime(prestige, now.plusDays(4).withHour(21).withMinute(0), 30, 30));
            }
        };
    }
}