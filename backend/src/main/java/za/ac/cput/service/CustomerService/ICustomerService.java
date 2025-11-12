package za.ac.cput.service.CustomerService;

import za.ac.cput.domain.Customer;
import za.ac.cput.service.IService;

import java.util.List;

public interface ICustomerService extends IService <Customer,Long> {
    List<Customer>getAllCustomers();
}
