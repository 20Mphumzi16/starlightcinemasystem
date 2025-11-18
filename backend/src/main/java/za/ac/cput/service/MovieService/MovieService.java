package za.ac.cput.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Movie;
import za.ac.cput.repository.MovieRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieService implements IMovieService  {

    private MovieRepository movieRepository;
    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie read(Long aLong) {
        return movieRepository.findById(aLong).orElse(null);
    }

    @Override
    public Movie update(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public boolean delete(Long aLong) {

        movieRepository.deleteById(aLong);
        return !movieRepository.existsById(aLong);

    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getByTitle(String title) {
        return movieRepository.findMovieByTitleContainingIgnoreCase(title);
    }

    public long getTotalMovies() {
        return movieRepository.count();
    }
}
