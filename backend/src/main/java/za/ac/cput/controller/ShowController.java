package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Show;
import za.ac.cput.dto.SeatStatusDTO;
import za.ac.cput.service.ShowService.ShowService;

import java.util.List;
@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/show")
public class ShowController {

    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping("/create")
    public Show create(@RequestBody Show show) {
        return showService.create(show);
    }

    @GetMapping("/read/{id}")
    public Show read(@PathVariable Long id) {
        return showService.read(id);
    }

    @PostMapping("/update")
    public Show update(@RequestBody Show show) {
        return showService.update(show);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return showService.delete(id);
    }

    @GetMapping("/get-all")
    public List<Show> getAll() {
        return showService.getAllShows();
    }

    @GetMapping("/by-movie/{id}")
    public List<Show> getByMovie(@PathVariable Long id) {
        return showService.getShowsByMovieId(id);
    }

    @GetMapping("/get-show-count")
    public long getShowCount() {
       return showService.showCount();
    }

}
