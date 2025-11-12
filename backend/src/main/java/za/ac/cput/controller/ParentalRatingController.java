package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.ParentalRating;
import za.ac.cput.service.ParentalRatingService.ParentalRatingService;

import java.util.List;

@RestController()
@RequestMapping("/pg")
public class ParentalRatingController {

    private ParentalRatingService parentalRatingService;
    @Autowired
    public ParentalRatingController(ParentalRatingService parentalRatingService) {
        this.parentalRatingService = parentalRatingService;
    }

    @PostMapping("/create")
    public ParentalRating create(@RequestBody ParentalRating parentalRating) {
        return parentalRatingService.create(parentalRating);
    }

    @GetMapping("/read/{id}")
    public ParentalRating read(@PathVariable Long id) {
        return parentalRatingService.read(id);
    }

    @PostMapping("/update")
    public ParentalRating update(@RequestBody ParentalRating parentalRating) {
        return parentalRatingService.update(parentalRating);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return parentalRatingService.delete(id);
    }

    @GetMapping("/get-all")
    public List<ParentalRating> getAll() {
        System.out.println("We here");
        return parentalRatingService.getAll();
    }
}
