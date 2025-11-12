package za.ac.cput.service.ParentalRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.ParentalRating;
import za.ac.cput.repository.ParentalRatingRepository;

import java.util.List;

@Service
public class ParentalRatingService implements IParentalRating {

    private final ParentalRatingRepository parentalRatingRepository;

    @Autowired
    public ParentalRatingService(ParentalRatingRepository parentalRatingRepository) {
        this.parentalRatingRepository = parentalRatingRepository;
    }

    @Override
    public List<ParentalRating> getAll() {
        return parentalRatingRepository.findAll();
    }

    @Override
    public ParentalRating create(ParentalRating parentalRating) {
        return parentalRatingRepository.save(parentalRating);
    }

    @Override
    public ParentalRating read(Long aLong) {
        return parentalRatingRepository.findById(aLong).orElse(null);
    }

    @Override
    public ParentalRating update(ParentalRating parentalRating) {
        return parentalRatingRepository.save(parentalRating);
    }

    @Override
    public boolean delete(Long aLong) {
        parentalRatingRepository.deleteById(aLong);
        return !parentalRatingRepository.existsById(aLong);
    }
}
