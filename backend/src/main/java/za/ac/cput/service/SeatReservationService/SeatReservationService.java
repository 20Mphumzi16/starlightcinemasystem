package za.ac.cput.service.SeatReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Seat;
import za.ac.cput.domain.SeatReservation;
import za.ac.cput.repository.SeatReservationRepository;

import java.util.List;
//Service Test TBD
@Service
public class SeatReservationService implements ISeatReservationService {

    private final SeatReservationRepository seatReservationRepository;

    @Autowired
    public SeatReservationService(SeatReservationRepository seatReservationRepository) {
        this.seatReservationRepository = seatReservationRepository;
    }



    @Override
    public SeatReservation create(SeatReservation seatReservation) {
        return seatReservationRepository.save(seatReservation);
    }

    @Override
    public SeatReservation read(Long aLong) {
        return seatReservationRepository.findById(aLong).orElse(null);
    }

    @Override
    public SeatReservation update(SeatReservation seatReservation) {
        return seatReservationRepository.save(seatReservation);
    }

    @Override
    public boolean delete(Long id) {
        seatReservationRepository.deleteById(id);
        return !seatReservationRepository.existsById(id);
    }

    @Override
    public List<SeatReservation> getAllSeatReservations() {
        return seatReservationRepository.findAll();
    }
    public List<SeatReservation> getSeatReservationsByShow(Long id) {
        return seatReservationRepository.findAllByBooking_Show_Id(id);
    }
}
