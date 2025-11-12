package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Address;

import static org.junit.jupiter.api.Assertions.*;

class AddressFactoryTest {

    private static Address address1;
    private static Address address2;
    private static Address address3;

    @BeforeAll
    static void setUp() {

        System.out.println("=====================================SETUP===============================================");

        address1=AddressFactory.buildAddress("18 Nyaniso Crescent","Goodwood","Cape Town","6548");
        address2=AddressFactory.buildAddress("27 Oxbow Cresent","Century City","Cape Town","8547");
        address3=AddressFactory.buildAddress("54 Elyja Bakrayi","Waterfront","Cape Town","7759");

    }

    @Test
    void buildAddress() {
        assertNotNull(address1);
        System.out.println(address1);

        assertNotNull(address2);
        System.out.println(address2);

        assertNotNull(address3);
        System.out.println(address3);
    }
}