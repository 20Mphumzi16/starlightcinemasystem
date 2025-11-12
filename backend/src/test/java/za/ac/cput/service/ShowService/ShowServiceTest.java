package za.ac.cput.service.ShowService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.*;
import za.ac.cput.factory.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShowServiceTest {


    private  ShowService showService;

    @Autowired
    public ShowServiceTest(ShowService showService) {
        this.showService = showService;
    }

    private static Show show1;
    private static Show show2;
    private static Show show3;

    private static Address address1;
    private static Address address2;
    private static Address address3;

    private static Movie movie1;
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

    private static Branch branch1;
    private static Branch branch2;
    private static Branch branch3;

    private static Hall hall1;
    private static Hall hall2;
    private static Hall hall3;



    @BeforeAll
    static void setUp() {


        address1= AddressFactory.buildAddress("18 Nyaniso Crescent","Goodwood","Cape Town","6548");
        address2=AddressFactory.buildAddress("27 Oxbow Cresent","Century City","Cape Town","8547");
        address3=AddressFactory.buildAddress("54 Elyja Bakrayi","Waterfront","Cape Town","7759");

        branch1= BranchFactory.createBranch("Starlight GoodWood",address1,"0215805117") ;
        branch2=BranchFactory.createBranch("Starlight Century",address2,"0218736358") ;
        branch3=BranchFactory.createBranch("Starlight Waterfront",address3,"0218186749") ;

        hall1= HallFactory.createHall("Hall A",80,branch1);
        hall2=HallFactory.createHall("Hall B",80,branch2);
        hall3=HallFactory.createHall("Hall C",80,branch3);


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

        show1= ShowFactory.createShow(movie1,hall1, LocalDateTime.of(2025,11,12,9,0),LocalDateTime.of(2025,11,12,11,0),370,ShowStatus.SCHEDULED);
        show2=ShowFactory.createShow(movie2,hall2, LocalDateTime.of(2025,10,16,15,0),LocalDateTime.of(2025,10,16,17,0),980,ShowStatus.SCHEDULED);
        show3=ShowFactory.createShow(movie3,hall3, LocalDateTime.of(2025,9,17,7,30),LocalDateTime.of(2025,9,17,10,0),394,ShowStatus.SCHEDULED);

    }

    @Test
    @Order(1)
    void create() {

        Show createdShow1=showService.create(show1);
        assertNotNull(createdShow1);
        System.out.println(createdShow1);

        Show createdShow2=showService.create(show2);
        assertNotNull(createdShow2);
        System.out.println(createdShow2);

        Show createdShow3=showService.create(show3);
        assertNotNull(createdShow3);
        System.out.println(createdShow3);

    }

    @Test
    @Order(2)
    void read() {

        Show readShow=showService.read(1L);
        assertNotNull(readShow);
        System.out.println(readShow);
    }

    @Test
    @Order(3)
    void update() {

        Show showToUpdate=new Show.ShowBuilder().copy(show1).setStatus(ShowStatus.RUNNING).build();
        Show updatedShow=showService.update(showToUpdate);
        assertNotNull(updatedShow);
        System.out.println(updatedShow);

    }

    @Test
    @Disabled
    @Order(4)
    void delete() {
        boolean deleted=showService.delete(3L);
        assertTrue(deleted);
        System.out.println("Deleted successfully");
    }

    @Test
    @Order(5)
    void getAllShows() {
        List<Show>allShows=showService.getAllShows();
        assertNotNull(allShows);
        System.out.println(allShows);
    }
}