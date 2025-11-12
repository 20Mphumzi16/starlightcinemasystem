package za.ac.cput.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Address;
import za.ac.cput.repository.AddressRepository;

import java.util.List;

@Service
public class AddressService implements IAddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }



    @Override
    public Address create(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address read(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address update(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public boolean delete(Long id) {
        addressRepository.deleteById(id);
        return !addressRepository.existsById(id);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

}
