package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Branch;
import za.ac.cput.service.BranchService.BranchService;

import java.util.List;
@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/create")
    public Branch create(@RequestBody Branch branch) {
        return branchService.create(branch);
    }

    @GetMapping("/read/{id}")
    public Branch read(@PathVariable Long id) {
        return branchService.read(id);
    }

    @PostMapping("/update")
    public Branch update(@RequestBody Branch branch) {
        return branchService.update(branch);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
      return  branchService.delete(id);
    }

    @GetMapping("/get-all")
    public List<Branch> getAll() {
       return branchService.getAllBranches();
    }
}
