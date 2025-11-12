package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.ParentalRating;

public interface ParentalRatingRepository extends JpaRepository<ParentalRating, Long> {

    ParentalRating findByRating(String rating);

}
