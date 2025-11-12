package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Movie;
import za.ac.cput.service.MovieService.MovieService;

import java.util.List;
@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/create")
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.create(movie);
    }

    @GetMapping("/read/{id}")
    public Movie readMovie(@PathVariable Long id) {
        return movieService.read(id);
    }

    @PostMapping("/update")
    public Movie updateMovie(@RequestBody Movie movie) {
        return movieService.update(movie);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteMovie(@PathVariable Long id) {
        return movieService.delete(id);
    }

    @GetMapping("/get-all")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }


    @GetMapping("/total-movies")
    public long getTotalMovies() {
        return movieService.getTotalMovies();

    }

}
