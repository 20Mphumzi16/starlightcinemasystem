package za.ac.cput.service.MovieService;

import za.ac.cput.domain.Movie;
import za.ac.cput.service.IService;

import java.util.List;

public interface IMovieService extends IService<Movie,Long> {

    List<Movie>getAllMovies();

}
