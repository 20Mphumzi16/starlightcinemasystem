package za.ac.cput.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Hall;
import za.ac.cput.domain.Seat;
import za.ac.cput.domain.SeatReservation;
import za.ac.cput.dto.SeatStatusDTO;
import za.ac.cput.repository.SeatRepository;
import za.ac.cput.repository.SeatReservationRepository;
import za.ac.cput.service.SeatReservationService.SeatReservationService;
import za.ac.cput.service.SeatService.SeatService;
import za.ac.cput.service.ShowService.ShowService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GetSeatAvailabilityByShowService {
    private final SeatService seatService;
    private final SeatReservationService seatReservationService;
    private final ShowService showService;

    @Autowired
    public GetSeatAvailabilityByShowService(
            SeatService seatService,
            SeatReservationService seatReservationService,
            ShowService showService
    ) {
        this.seatService = seatService;
        this.seatReservationService = seatReservationService;
        this.showService = showService;
    }

    public List<SeatStatusDTO> getSeatAvailabilityByShow(Long showId) {

        // Step 1 Get the show directly
        var show = showService.read(showId);

        // Step 2 Get the hall from the show
        Hall hall = show.getHall();
        if (hall == null) {
            throw new RuntimeException("No hall associated with show ID " + showId);
        }

        // Step 3 Get all reserved seats for this show
        List<SeatReservation> reservedSeats = seatReservationService.getSeatReservationsByShow(showId);

        // Step 4 Extract occupied seat IDs
        Set<Long> occupiedIds = reservedSeats.stream()
                .map(r -> r.getSeat().getId())
                .collect(Collectors.toSet());

        //Step 5 Get all seats in that hall
        List<Seat> seats = seatService.getAllByHall(hall.getId());

        // Step 6 Combine results into DTO
        return seats.stream()
                .map(s -> new SeatStatusDTO(
                        s.getId(),
                        s.getSeatNumber(),
                        occupiedIds.contains(s.getId())
                ))
                .collect(Collectors.toList());
    }
}

