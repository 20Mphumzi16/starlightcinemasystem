package za.ac.cput.service.HallService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Hall;
import za.ac.cput.repository.HallRepository;

import java.util.List;
@Service
public class HallService  implements IHallService {

    private HallRepository hallRepository;

    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall create(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public Hall read(Long id) {
        return hallRepository.findById(id).orElse(null);
    }

    @Override
    public Hall update(Hall hall) {
        return hallRepository.save(hall);

    }

    @Override
    public boolean delete(Long id) {
        hallRepository.deleteById(id);
        return !hallRepository.existsById(id);

    }

    public List<Hall> getBranchHalls(Long id){
        return hallRepository.findAllByBranch_Id(id);
    }

    @Override
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

}
