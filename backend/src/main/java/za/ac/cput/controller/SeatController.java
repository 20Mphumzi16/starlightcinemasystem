package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.api.GetSeatAvailabilityByShowService;
import za.ac.cput.domain.Seat;
import za.ac.cput.dto.SeatStatusDTO;
import za.ac.cput.service.SeatService.SeatService;

import java.util.List;
@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;
    private final GetSeatAvailabilityByShowService getSeatAvailabilityByShowService;
    @Autowired
    public SeatController(SeatService seatService, GetSeatAvailabilityByShowService getSeatAvailabilityByShowService) {
        this.seatService = seatService;
        this.getSeatAvailabilityByShowService = getSeatAvailabilityByShowService;
    }

    @PostMapping("/create")
    public Seat create(@RequestBody Seat seat) {
        return seatService.create(seat);
    }

    @GetMapping("/read/{id}")
    public Seat read(@PathVariable Long id) {
        return seatService.read(id);

    }

    @PostMapping("/update")
    public Seat update(@RequestBody Seat seat) {
        return seatService.update(seat);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return seatService.delete(id);
    }

    @GetMapping("/get-all")
    public List<Seat> getAll() {
        return seatService.getAllSeats();
    }


    @GetMapping("/seats/{showId}")
    public ResponseEntity<List<SeatStatusDTO>> getSeatsForShow(@PathVariable Long showId) {
        List<SeatStatusDTO> seats =getSeatAvailabilityByShowService.getSeatAvailabilityByShow(showId);
        return ResponseEntity.ok(seats);
    }
}
