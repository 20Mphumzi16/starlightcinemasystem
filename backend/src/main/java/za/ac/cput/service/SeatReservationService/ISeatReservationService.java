package za.ac.cput.service.SeatReservationService;

import za.ac.cput.domain.SeatReservation;
import za.ac.cput.service.IService;

import java.util.List;

public interface ISeatReservationService extends IService <SeatReservation,Long> {
    List<SeatReservation>getAllSeatReservations();
}
