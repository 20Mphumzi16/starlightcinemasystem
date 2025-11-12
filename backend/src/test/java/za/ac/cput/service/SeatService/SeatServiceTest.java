package za.ac.cput.service.SeatService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Hall;
import za.ac.cput.domain.Seat;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.BranchFactory;
import za.ac.cput.factory.HallFactory;
import za.ac.cput.factory.SeatFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeatServiceTest {

    private  SeatService seatService;
    private static  Seat seat1;
    private static Seat seat2;
    private static Seat seat3;

    private static Hall hall1;
    private static Hall hall2;
    private static Hall hall3;

    private static Branch branch1;
    private static Branch branch2;
    private static Branch branch3;

    private static Address address1;
    private static Address address2;
    private static Address address3;

    private static List<Seat> seatListHall1;
    private static List<Seat> seatListHall2;
    private static List<Seat> seatListHall3;

    @Autowired
    public SeatServiceTest(SeatService seatService) {
        this.seatService = seatService;
    }

    @BeforeAll
    static void setUp() {

        address1= AddressFactory.buildAddress("18 Nyaniso Crescent","Goodwood","Cape Town","6548");
        address2=AddressFactory.buildAddress("27 Oxbow Cresent","Century City","Cape Town","8547");
        address3=AddressFactory.buildAddress("54 Elyja Bakrayi","Waterfront","Cape Town","7759");

        branch1= BranchFactory.createBranch("Starlight GoodWood",address1,"0215805117") ;
        branch2=BranchFactory.createBranch("Starlight Century",address2,"0218736358") ;
        branch3=BranchFactory.createBranch("Starlight Waterfront",address3,"0218186749") ;

        hall1= HallFactory.createHall("Hall A",48,branch1);
        hall2=HallFactory.createHall("Hall B",48,branch2);
        hall3=HallFactory.createHall("Hall C",48,branch3);

//        seat1=new Seat.SeatBuilder().setSeatNumber("A15").setHall(hall1).build();
//        seat2=new Seat.SeatBuilder().setSeatNumber("B16").setHall(hall2).build();
//        seat3=new Seat.SeatBuilder().setSeatNumber("C7").setHall(hall3).build();

        // Generate seats for each hall
        seatListHall1 = generateSeats(hall1);
        seatListHall2 = generateSeats(hall2);
        seatListHall3 = generateSeats(hall3);
    }

    private static List<Seat> generateSeats(Hall hall) {
        List<Seat> seats = new ArrayList<>();
        char[] rows = {'A', 'B', 'C', 'D', 'E', 'F'}; // 6 rows
        int seatsPerRow = 8;

        for (char row : rows) {
            for (int num = 1; num <= seatsPerRow; num++) {
                String seatNumber = row + String.valueOf(num);
                Seat seat = new Seat.SeatBuilder()
                        .setSeatNumber(seatNumber)
                        .setHall(hall)
                        .build();
                seats.add(seat);
            }
        }
        return seats;
    }

    @Test
    @Order(1)
    void create() {
        seatListHall1.forEach(seat -> {
            Seat created = seatService.create(seat);
            assertNotNull(created);
            System.out.println(created);
        });

        seatListHall2.forEach(seat -> {
            Seat created = seatService.create(seat);
            assertNotNull(created);
            System.out.println(created);
        });

        seatListHall3.forEach(seat -> {
            Seat created = seatService.create(seat);
            assertNotNull(created);
            System.out.println(created);
        });
    }


    @Test
    @Order(2)
    void read() {

        Seat readSeat= seatService.read(3L);
        assertNotNull(readSeat);
        System.out.println(readSeat);
    }

    @Test
    @Order(3)
    void update() {

        Seat seatToUpdate=new Seat.SeatBuilder().copy(seat1).setSeatNumber("A17").setHall(hall1).build();
        Seat updatedSeat=seatService.update(seatToUpdate);
        assertNotNull(updatedSeat);
        System.out.println(updatedSeat);

    }

    @Test
    @Order(3)
    void delete() {
        boolean deleted=seatService.delete(3L);
        assertTrue(deleted);
        System.out.println("Deleted successfully");
    }

    @Test
    @Order(4)
    void getAllSeats() {
        List<Seat> allSeats=seatService.getAllSeats();
        assertNotNull(allSeats);
        System.out.println(allSeats);
    }
}