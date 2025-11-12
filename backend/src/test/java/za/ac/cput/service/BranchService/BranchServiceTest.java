package za.ac.cput.service.BranchService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Branch;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.BranchFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BranchServiceTest {

    private BranchService branchService;
    private static Branch branch1;
    private static Branch branch2;
    private static Branch branch3;
    private static Address address1;
    private static Address address2;
    private static Address address3;

    @Autowired
    public BranchServiceTest(BranchService branchService) {
        this.branchService = branchService;
    }

    @BeforeAll
    static void setUp() {


        System.out.println("=====================================SETUP===============================================");


        address1= AddressFactory.buildAddress("18 Nyaniso Crescent","Goodwood","Cape Town","6548");
        address2=AddressFactory.buildAddress("27 Oxbow Cresent","Century City","Cape Town","8547");
        address3=AddressFactory.buildAddress("54 Elyja Bakrayi","Waterfront","Cape Town","7759");

        branch1= BranchFactory.createBranch("Starlight GoodWood",address1,"0215805117") ;
        branch2=BranchFactory.createBranch("Starlight Century",address2,"0218736358") ;
        branch3=BranchFactory.createBranch("Starlight Waterfront",address3,"0218186749") ;

    }

    @Test
    @Order(1)
    void create() {

        System.out.println("=====================================CREATE===============================================");


        Branch createdBranch1 = branchService.create(branch1);
        assertNotNull(createdBranch1);
        System.out.println(createdBranch1);

        Branch createdBranch2 = branchService.create(branch2);
        assertNotNull(createdBranch2);
        System.out.println(createdBranch2);

        Branch createdBranch3 = branchService.create(branch3);
        assertNotNull(createdBranch3);
        System.out.println(createdBranch3);


    }

    @Test
    @Order(2)
    void read() {

        System.out.println("=====================================READ===============================================");


        Branch readBranch= branchService.read(3L);
        assertNotNull(readBranch);
        System.out.println(readBranch);
    }

    @Test
    @Order(3)
    void update() {

        System.out.println("=====================================UPDATE===============================================");


        Branch branchToUpdate=new Branch.BranchBuilder().copy(branch2).setName("Update Test").build();
        Branch updatedBranch=branchService.update(branchToUpdate);
        assertNotNull(updatedBranch);
        System.out.println(updatedBranch);

    }

    @Test
    @Order(4)
    void delete() {


        System.out.println("=====================================DELETE===============================================");


        boolean deleted=branchService.delete(3L);
        assertTrue(deleted);
        System.out.println("Deleted Successfully");
    }

    @Test
    @Order(5)
    void getAllBranches() {

        System.out.println("=====================================GETALL===============================================");


        List<Branch> allBranches=branchService.getAllBranches();
        assertNotNull(allBranches);
        System.out.println(allBranches);
    }
}