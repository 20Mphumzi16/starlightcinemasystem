package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.api.GetSeatAvailabilityByShowService;
import za.ac.cput.domain.SeatReservation;
import za.ac.cput.dto.SeatStatusDTO;
import za.ac.cput.service.SeatReservationService.SeatReservationService;

import java.util.List;
@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/seat-reservation")
public class SeatReservationController {

    public final SeatReservationService seatReservationService;

    @Autowired
    public SeatReservationController(SeatReservationService seatReservationService, GetSeatAvailabilityByShowService getSeatAvailabilityByShowService) {
        this.seatReservationService = seatReservationService;
    }

    @PostMapping("/create")
    public SeatReservation create(@RequestBody SeatReservation seatReservation) {
        return seatReservationService.create(seatReservation);
    }

    @GetMapping("/read/{id}")
    public SeatReservation read(@PathVariable Long id) {
        return seatReservationService.read(id);
    }

    @PostMapping("/update")
    public SeatReservation update(@RequestBody SeatReservation seatReservation) {
        return seatReservationService.update(seatReservation);
    }
    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return seatReservationService.delete(id);
    }

    @GetMapping("/get-all")
    public List<SeatReservation> getAll() {
        return seatReservationService.getAllSeatReservations();
    }


}
