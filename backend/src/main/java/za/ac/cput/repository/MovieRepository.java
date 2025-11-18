package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Movie;


import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
   List<Movie>  findMovieByTitleContainingIgnoreCase(String title);


}
