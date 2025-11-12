package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Branch;

import static org.junit.jupiter.api.Assertions.*;

class BranchFactoryTest {

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


    }

    @Test
    void createBranch() {
        assertNotNull(branch1);
        assertNotNull(branch2);
        assertNotNull(branch3);

        System.out.println(branch1);
        System.out.println(branch2);
        System.out.println(branch3);
    }
}