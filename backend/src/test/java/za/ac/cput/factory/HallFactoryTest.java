package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Hall;

import static org.junit.jupiter.api.Assertions.*;

class HallFactoryTest {

    private static Hall hall1;
    private static Hall hall2;
    private static Hall hall3;
    private static Branch branch1;
    private static Branch branch2;
    private static Branch branch3;
    private static Address address1;
    private static Address address2;
    private static Address address3;


    @BeforeAll
    static void setUp() {

        System.out.println("=====================================SETUP===============================================");


        address1=AddressFactory.buildAddress("18 Nyaniso Crescent","Goodwood","Cape Town","6548");
        address2=AddressFactory.buildAddress("27 Oxbow Cresent","Century City","Cape Town","8547");
        address3=AddressFactory.buildAddress("54 Elyja Bakrayi","Waterfront","Cape Town","7759");

        branch1=BranchFactory.createBranch("Starlight GoodWood",address1,"0215805117") ;
        branch2=BranchFactory.createBranch("Starlight Century",address2,"0218736358") ;
        branch3=BranchFactory.createBranch("Starlight Waterfront",address3,"0218186749") ;

        hall1=HallFactory.createHall("Hall A",80,branch1);
        hall2=HallFactory.createHall("Hall B",80,branch2);
        hall3=HallFactory.createHall("Hall C",80,branch3);

    }

    @Test
    void createHall() {

        assertNotNull(hall1);
        assertNotNull(hall2);
        assertNotNull(hall3);

        System.out.println(hall1);
        System.out.println(hall2);
        System.out.println(hall3);


    }
}