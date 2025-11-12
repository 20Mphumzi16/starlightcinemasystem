package za.ac.cput.service.SeatService;

import za.ac.cput.domain.Seat;
import za.ac.cput.service.IService;

import java.util.List;

public interface ISeatService extends IService <Seat,Long> {
        List<Seat>getAllSeats();
}
