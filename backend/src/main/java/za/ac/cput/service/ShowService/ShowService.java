package za.ac.cput.service.ShowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Show;
import za.ac.cput.repository.ShowRepository;

import java.util.List;

@Service
public class ShowService implements IShowService {

    private ShowRepository showRepository;

    @Autowired
    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }


    @Override
    public Show create(Show show) {
        return showRepository.save(show);
    }

    @Override
    public Show read(Long id) {
        return showRepository.findById(id).orElse(null);
    }

    @Override
    public Show update(Show show) {
        return showRepository.save(show);
    }

    @Override
    public boolean delete(Long aLong) {
        showRepository.deleteById(aLong);
        return !showRepository.existsById(aLong);
    }

    @Override
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public long showCount() {
        return showRepository.count();
    }

    public List<Show> getShowsByMovieId(Long id) {
        return showRepository.findAllByMovie_Id(id);
    }
}
