package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SeatReservationFactoryTest {

    private static  Seat seat1;
    private static Seat seat2;
    private static Seat seat3;
    private static SeatReservation seatReservation1;
    private static SeatReservation seatReservation2;
    private static SeatReservation seatReservation3;

    private static Customer customer1;
    private static Customer customer2;
    private static Customer customer3;

    private static Gender gender1;
    private static Gender gender2;
    private static Gender gender3;

    private static Role role1;

    private static Set<Role>roles1;

    private static Booking booking1;
    private static Booking booking2;
    private static  Booking booking3;
    private static Show show1;
    private static Show show2;
    private static Show show3;

    private static Address address1;
    private static Address address2;
    private static Address address3;

    private static  Movie movie1;
    private static  Movie movie2;
    private static  Movie movie3;
    private static ParentalRating rating1;
    private static ParentalRating rating2;
    private static ParentalRating rating3;

    private static Genre genre1;
    private static Genre genre2;
    private static Genre genre3;

    private static Set<Genre> genres1;
    private static Set<Genre> genres2;
    private static Set<Genre> genres3;
    private static Hall hall1;
    private static Hall hall2;
    private static Hall hall3;
    private static Branch branch1;
    private static Branch branch2;
    private static Branch branch3;



    @BeforeAll
    static void setUp() {



        address1=AddressFactory.buildAddress("18 Nyaniso Crescent","Goodwood","Cape Town","6548");
        address2=AddressFactory.buildAddress("27 Oxbow Cresent","Century City","Cape Town","8547");
        address3=AddressFactory.buildAddress("54 Elyja Bakrayi","Waterfront","Cape Town","7759");

        branch1=BranchFactory.createBranch("Starlight GoodWood",address1,"0215805117") ;
        branch2=BranchFactory.createBranch("Starlight Century",address2,"0218736358") ;
        branch3=BranchFactory.createBranch("Starlight Waterfront",address3,"0218186749") ;

        hall1=HallFactory.createHall("Hall A",84,branch1);
        hall2=HallFactory.createHall("Hall B",84,branch2);
        hall3=HallFactory.createHall("Hall C",84,branch3);

        seat1=new Seat.SeatBuilder().setSeatNumber("A15").setHall(hall1).build();
        seat2=new Seat.SeatBuilder().setSeatNumber("B16").setHall(hall2).build();
        seat3=new Seat.SeatBuilder().setSeatNumber("C7").setHall(hall3).build();



        genre1=new Genre.Builder().setName("Action").build();
        genre2=new Genre.Builder().setName("Comedy").build();
        genre3=new Genre.Builder().setName("Romantic").build();

        rating1=new ParentalRating.ParentalRatingBuilder().setRating("PG-13").setDescription("May contain fighting scenes").build();
        rating2=new ParentalRating.ParentalRatingBuilder().setRating("PG-16").setDescription("Contains Graphic Content").build();
        rating3=new ParentalRating.ParentalRatingBuilder().setRating("PG").setDescription("Parental Guidance is advised").build();

        genres1=new HashSet<>();
        genres2=new HashSet<>();
        genres3=new HashSet<>();

        genres1.add(genre1);
        genres2.add(genre2);
        genres3.add(genre3);





        movie1=MovieFactory.createMovie("Fantastic 4",new byte[0], LocalDate.of(2024,6,20),genres1,160,rating1,"The fantstic 4 is back on track to save the world.");
        movie2=MovieFactory.createMovie("Expandables",new byte[0],LocalDate.of(2025,1,20),genres2,92,rating2,"Dom is kidnapped and the Expandables are born again.");
        movie3=MovieFactory.createMovie("Love Island",new byte[0],LocalDate.of(2024,2,17),genres3,90,rating3,"TLove is in the air in this city.");

        show1=ShowFactory.createShow(movie1,hall1, LocalDateTime.of(2025,11,12,9,0),LocalDateTime.of(2025,11,12,11,0),370, ShowStatus.SCHEDULED);
        System.out.println(show1);
        show2=ShowFactory.createShow(movie2,hall2, LocalDateTime.of(2025,10,16,15,0),LocalDateTime.of(2025,10,16,17,0),980,ShowStatus.SCHEDULED);
        System.out.println(show2);

        show3=ShowFactory.createShow(movie3,hall2, LocalDateTime.of(2025,9,17,7,30),LocalDateTime.of(2025,9,17,10,0),394,ShowStatus.SCHEDULED);
        System.out.println(show3);

        role1=new Role("Customer");


        Set<Role> roles1=new HashSet<>();
        roles1.add(role1);


        gender1=new Gender.GenderBuilder().setName("Male").build();
        gender2=new Gender.GenderBuilder().setName("Female").build();
        gender3=new Gender.GenderBuilder().setName("Other").build();

        customer1 = CustomerFactory.createCustomer("Mpumzi","Mbula",new byte[0],gender1,"mphumzimbula@gmail.com","20Mphmbu16!","0658436358", LocalDate.of(2000,6,20),roles1);
        System.out.println(customer1);

        customer2 = CustomerFactory.createCustomer("Vuyokazi","Xayiya",new byte[0],gender2,"vuyokazixayiya@gmail.com","20VeeYiya16!","0835805117", LocalDate.of(2000,1,14),roles1);
        System.out.println(customer2);
        customer3 = CustomerFactory.createCustomer("Nombulelo","Pretty","Mbula",new byte[0],gender2,"buliembula@gmail.com","Bulie123!","0618227474", LocalDate.of(2002,5,17),roles1);
        System.out.println(customer3);

        booking1=BookingFactory.createBooking(show1,customer1,BookingStatus.Confirmed,LocalDateTime.now(),580);
        booking2=BookingFactory.createBooking(show2,customer2,BookingStatus.Confirmed,LocalDateTime.now(),700);
        booking3=BookingFactory.createBooking(show3,customer3,BookingStatus.Confirmed,LocalDateTime.now(),500);


        seatReservation1=SeatReservationFactory.createSeatReservation(seat1,booking1,SeatStatus.RESERVED);
        seatReservation2=SeatReservationFactory.createSeatReservation(seat2,booking2,SeatStatus.RESERVED);
        seatReservation3=SeatReservationFactory.createSeatReservation(seat3,booking3,SeatStatus.RESERVED);



    }

    @Test
    void createSeatReservation() {
        assertNotNull(seatReservation1);
        assertNotNull(seatReservation2);
        assertNotNull(seatReservation3);

        System.out.println(seatReservation1);
        System.out.println(seatReservation2);
        System.out.println(seatReservation3);
    }
}