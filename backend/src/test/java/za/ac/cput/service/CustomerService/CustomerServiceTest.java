package za.ac.cput.service.CustomerService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.api.AssignWishlistToCustomerService;
import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Gender;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.factory.CustomerFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {

    //For the create test to pass Go to Role Service and run create then come back and run CustomerServiceTest

    private  CustomerService customerService;

    private static Customer customer1;
    private static Customer customer2;
    private static Customer customer3;

    private static Gender gender1;
    private static Gender gender2;
    private static Gender gender3;

    private static Role role1;

    @Autowired
    public CustomerServiceTest(CustomerService customerService) {
        this.customerService = customerService;

    }

    @BeforeAll
    static void setUp() {

        role1=new Role("Customer");


        Set<Role>roles1=new HashSet<>();
        roles1.add(role1);


        gender1=new Gender.GenderBuilder().setName("Male").build();
        gender2=new Gender.GenderBuilder().setName("Female").build();
        gender3=new Gender.GenderBuilder().setName("Other").build();

        customer1 = CustomerFactory.createCustomer("Mpumzi","Mbula",new byte[0],gender1,"mphumzimbula@gmail.com","20Mphmbu16!","0658436358", LocalDate.of(2000,6,20),roles1);
        customer2 = CustomerFactory.createCustomer("Vuyokazi","Xayiya",new byte[0],gender2,"vuyokazixayiya@gmail.com","20VeeYiya16!","0835805117", LocalDate.of(2000,1,14),roles1);
        customer3 = CustomerFactory.createCustomer("Nombulelo","Pretty","Mbula",new byte[0],gender3,"buliembula@gmail.com","Bulie123!","0618227474", LocalDate.of(2002,5,17),roles1);

    }

    @Test
    @Order(1)
    void create() {
        Customer createdCustomer1 = customerService.create(customer1);

        assertNotNull(createdCustomer1);
        System.out.println(createdCustomer1);


//        Customer createdCustomer2 = customerService.create(customer2);
//        assertNotNull(createdCustomer2);
//        System.out.println(createdCustomer2);
//
//        Customer createdCustomer3 = customerService.create(customer3);
//        assertNotNull(createdCustomer3);
//        System.out.println(createdCustomer3);

    }

    @Test
    @Order(2)
    void read() {

        Customer readCustomer = customerService.read(1L);
        assertNotNull(readCustomer);
        System.out.println(readCustomer);
    }

    @Test
    @Order(3)
    void update() {

        Customer customerToUpdate=new Customer.CustomerBuilder().copy( customer1 ).setPassword("Awomi@2021").build();
        Customer updatedCustomer = customerService.update(customerToUpdate);
        assertNotNull(updatedCustomer);
        System.out.println(updatedCustomer);
    }

    @Test
    @Order(4)
    void delete() {
       boolean deleted= customerService.delete(3L);
       assertTrue(deleted);
        System.out.println("deleted");
    }

    @Test
    @Order(5)
    void getAllCustomers() {
        List<Customer> customerList = customerService.getAllCustomers();
        assertNotNull(customerList);
        System.out.println(customerList);
    }
    @Test
    @Order(6)
    void getByEmailAndPassword(){
        Customer readByEmailAndPassword=customerService.getByEmailAndPassword("mphumzimbula@gmail.com","20Mphmbu16!");
        assertNotNull(readByEmailAndPassword);
        System.out.println(readByEmailAndPassword);
    }
}