package za.ac.cput.service.HallService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Hall;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.BranchFactory;
import za.ac.cput.factory.HallFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HallServiceTest {

    private static Hall hall1;
    private static Hall hall2;
    private static Hall hall3;
    private static Branch branch1;
    private static Branch branch2;
    private static Branch branch3;
    private static Address address1;
    private static Address address2;
    private static Address address3;

    private HallService hallService;

    @Autowired
    public HallServiceTest(HallService hallService) {
        this.hallService = hallService;
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

        hall1= HallFactory.createHall("Hall A",80,branch1);
        hall2=HallFactory.createHall("Hall B",80,branch2);
        hall3=HallFactory.createHall("Hall C",80,branch3);

    }

    @Test
    @Order(1)
    void create() {

        Hall createdHall1 = hallService.create(hall1);
        assertNotNull(createdHall1);
        System.out.println(createdHall1);

        Hall createdHall2 = hallService.create(hall2);
        assertNotNull(createdHall2);
        System.out.println(createdHall2);

        Hall createdHall3 = hallService.create(hall3);
        assertNotNull(createdHall3);
        System.out.println(createdHall3);
    }

    @Test
    @Order(2)
    void read() {

        Hall readHall= hallService.read(2L);
        assertNotNull(readHall);
        System.out.println(readHall);
    }

    @Test
    @Order(3)
    void update() {

        Hall hallToUpdate= new Hall.HallBuilder().copy(hall2).setName("ABC").build();
        Hall updatedHall=hallService.update(hallToUpdate);
        assertNotNull(updatedHall);
        System.out.println(updatedHall);
    }


    @Test
    @Order(4)
    void delete() {
        boolean isdeleted= hallService.delete(3L);
        assertTrue(isdeleted);
        System.out.println("Deleted successfully ");
    }

    @Test
    @Order(5)
    void getAllHalls() {
        List<Hall> allHalls= hallService.getAllHalls();
        assertNotNull(allHalls);
        System.out.println(allHalls);
    }
}