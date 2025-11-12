package za.ac.cput.service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Seat;
import za.ac.cput.repository.SeatRepository;

import java.util.List;

@Service
public class SeatService implements ISeatService {

    private SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }


    @Override
    public Seat create(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public Seat read(Long id) {
        return seatRepository.findById(id).orElse(null);
    }

    @Override
    public Seat update(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public boolean delete(Long id) {
        seatRepository.deleteById(id);
        return !seatRepository.existsById(id);
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
    public List<Seat>getAllByHall(Long id){
        return seatRepository.findAllByHall_Id(id);
    }
}
