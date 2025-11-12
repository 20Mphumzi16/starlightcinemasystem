package za.ac.cput.service.ParentalRatingService;

import za.ac.cput.domain.ParentalRating;
import za.ac.cput.service.IService;

import java.util.List;

public interface IParentalRating extends IService<ParentalRating,Long> {
    List<ParentalRating> getAll();
}
