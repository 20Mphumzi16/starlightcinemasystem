package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Hall;
import za.ac.cput.service.HallService.HallService;

import java.util.List;
@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/hall")
public class HallController {

    private final HallService hallService;

    @Autowired
    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping("/create")
    public Hall create(@RequestBody Hall hall) {
        return hallService.create(hall);
    }

    @GetMapping("/read/{id}")
    public Hall read(@PathVariable Long id) {
        return hallService.read(id);
    }

    @PostMapping("/update")
    public Hall update(@RequestBody Hall hall) {
        return hallService.update(hall);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
       return  hallService.delete(id);
    }

    @GetMapping("/get-all")
    public List<Hall> getAll() {
        return hallService.getAllHalls();
    }

    @GetMapping("/get-branch-halls/{branchId}")
    public List<Hall> getBranchHalls(@PathVariable Long branchId) {
        return hallService.getBranchHalls(branchId);
    }
}
