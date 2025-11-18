package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Admin;
import za.ac.cput.service.AdminService.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public Admin create(Admin admin) {
        return adminService.create(admin);
    }

    @GetMapping("/read/{id}")
    public Admin read(@PathVariable Long id) {
        return adminService.read(id);
    }

    @GetMapping("/login/{username}/{password}")
    public Admin login(@PathVariable String username, @PathVariable String password) {
        return adminService.getBYEmailAndPassword(username, password);
    }
}
