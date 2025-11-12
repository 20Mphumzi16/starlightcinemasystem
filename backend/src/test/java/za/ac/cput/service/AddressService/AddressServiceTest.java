package za.ac.cput.service.AddressService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.factory.AddressFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceTest {

    private AddressService addressService;

    private static Address address1;
    private static Address address2;
    private static Address address3;

    @Autowired
    public AddressServiceTest(AddressService addressService) {
        this.addressService = addressService;
    }

    @BeforeAll
    static void setUp() {
        System.out.println("=====================================SETUP===============================================");

        address1= AddressFactory.buildAddress("18 Nyaniso Crescent","Goodwood","Cape Town","6548");
        address2=AddressFactory.buildAddress("27 Oxbow Cresent","Century City","Cape Town","8547");
        address3=AddressFactory.buildAddress("54 Elyja Bakrayi","Waterfront","Cape Town","7759");
    }

    @Test
    @Order(1)
    void create() {
        Address createdAddress1 = addressService.create(address1);
        assertNotNull(createdAddress1);
        System.out.println(createdAddress1);

        Address createdAddress2 = addressService.create(address2);
        assertNotNull(createdAddress2);
        System.out.println(createdAddress2);

        Address createdAddress3 = addressService.create(address3);
        assertNotNull(createdAddress3);
        System.out.println(createdAddress3);

    }

    @Test
    @Order(2)
    void read() {
        Address readAddress=addressService.read(1L);
        assertNotNull(readAddress);
        System.out.println(readAddress);
    }

    @Test
    @Order(3)
    void update() {
        Address addressToUpdate=new Address.AddressBuilder().copy(address2).setPostalCode("1442").build();
        Address updatedAddress=addressService.update(addressToUpdate);
        assertNotNull(updatedAddress);
        System.out.println(updatedAddress);

    }

    @Test
    @Order(4)
    void delete() {
        boolean deleted=addressService.delete(3L);
        assertTrue(deleted);
        System.out.println("Deleted successfully");
    }

    @Test
    @Order(5)
    void getAllAddresses() {
        List<Address> addresses=addressService.getAllAddresses();
        assertNotNull(addresses);
        System.out.println(addresses);
    }
}