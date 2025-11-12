package za.ac.cput.service.AddressService;

import za.ac.cput.domain.Address;
import za.ac.cput.service.IService;

import java.util.List;

public interface IAddressService extends IService<Address,Long> {
    List<Address>getAllAddresses();
}
