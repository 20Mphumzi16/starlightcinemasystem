package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Address;
import za.ac.cput.service.AddressService.AddressService;

import java.util.List;
@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public Address create(@RequestBody Address address) {
        return addressService.create(address);
    }

    @GetMapping("/read/{id}")
    public Address read(@PathVariable Long id) {
        return addressService.read(id);
    }

    @PostMapping("/update")
    public Address update(@RequestBody Address address) {
        return addressService.update(address);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
       return addressService.delete(id);
    }

    @GetMapping("/get-all")
    public List<Address> getAll() {
        return addressService.getAllAddresses();
    }
}
