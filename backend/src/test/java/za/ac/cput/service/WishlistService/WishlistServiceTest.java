package za.ac.cput.service.WishlistService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.*;
import za.ac.cput.factory.*;
import za.ac.cput.service.RoleService.RoleService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistServiceTest {

    private  WishlistService wishlistService;


    private  static Wishlist wishList1;
    private  static Wishlist wishList2;
    private  static Wishlist wishList3;

    private static Customer customer1;
    private static Customer customer2;
    private static Customer customer3;

    private static Gender gender1;
    private static Gender gender2;
    private static Gender gender3;

    private static Role role1;

    private static Set<Role> roles1;


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

    private static Branch branch1;
    private static Branch branch2;
    private static Branch branch3;

    private static Hall hall1;
    private static Hall hall2;
    private static Hall hall3;



    @Autowired
    public WishlistServiceTest(WishlistService wishlistService,RoleService roleService) {
        this.wishlistService = wishlistService;
    }

    @BeforeAll
   static void setUp() {


        System.out.println("========================================SETUP====================================");

        role1 = new Role("Customer");

        Set<Role> roles1 = new HashSet<>();
        roles1.add(role1);


        gender1 = new Gender.GenderBuilder().setName("Male").build();
        gender2 = new Gender.GenderBuilder().setName("Female").build();
        gender3 = new Gender.GenderBuilder().setName("Other").build();

        customer1 = CustomerFactory.createCustomer("Mpumzi", "Mbula", new byte[0], gender1, "mphumzimbula@gmail.com", "20Mphmbu16!", "0658436358", LocalDate.of(2000, 6, 20), roles1);
        customer2 = CustomerFactory.createCustomer("Vuyokazi", "Xayiya", new byte[0], gender2, "vuyokazixayiya@gmail.com", "20VeeYiya16!", "0835805117", LocalDate.of(2000, 1, 14), roles1);
        customer3 = CustomerFactory.createCustomer("Nombulelo", "Pretty", "Mbula", new byte[0], gender3, "buliembula@gmail.com", "Bulie123!", "0618227474", LocalDate.of(2002, 5, 17), roles1);

        address1 = AddressFactory.buildAddress("18 Nyaniso Crescent", "Goodwood", "Cape Town", "6548");
        address2 = AddressFactory.buildAddress("27 Oxbow Cresent", "Century City", "Cape Town", "8547");
        address3 = AddressFactory.buildAddress("54 Elyja Bakrayi", "Waterfront", "Cape Town", "7759");

        branch1 = BranchFactory.createBranch("Starlight GoodWood", address1, "0215805117");
        branch2 = BranchFactory.createBranch("Starlight Century", address2, "0218736358");
        branch3 = BranchFactory.createBranch("Starlight Waterfront", address3, "0218186749");

        hall1 = HallFactory.createHall("Hall A", 80, branch1);
        hall2 = HallFactory.createHall("Hall B", 80, branch2);
        hall3 = HallFactory.createHall("Hall C", 80, branch3);


        genre1 = new Genre.Builder().setName("Action").build();
        genre2 = new Genre.Builder().setName("Comedy").build();
        genre3 = new Genre.Builder().setName("Romantic").build();

        rating1 = new ParentalRating.ParentalRatingBuilder().setRating("PG-13").setDescription("May contain fighting scenes").build();
        rating2 = new ParentalRating.ParentalRatingBuilder().setRating("PG-16").setDescription("Contains Graphic Content").build();
        rating3 = new ParentalRating.ParentalRatingBuilder().setRating("PG").setDescription("Parental Guidance is advised").build();

        genres1 = new HashSet<>();
        genres2 = new HashSet<>();
        genres3 = new HashSet<>();

        genres1.add(genre1);
        genres2.add(genre2);
        genres3.add(genre3);


        movie1=MovieFactory.createMovie("Fantastic 4",new byte[0], LocalDate.of(2024,6,20),genres1,"www.trailer.com","www.image.com",160,rating1,"The fantstic 4 is back on track to save the world.");
        movie2=MovieFactory.createMovie("Expandables",new byte[0],LocalDate.of(2025,1,20),genres2,"www.trailer.com","www.image.com",92,rating2,"Dom is kidnapped and the Expandables are born again.");
        movie3=MovieFactory.createMovie("Love Island",new byte[0],LocalDate.of(2024,2,17),genres3,"www.trailer.com","www.image.com",90,rating3,"TLove is in the air in this city.");

        Set<Movie> movieSet1 = new HashSet<>();
        Set<Movie> movieSet2 = new HashSet<>();
        Set<Movie> movieSet3 = new HashSet<>();

        movieSet1.add(movie1);
        movieSet1.add(movie2);

        movieSet2.add(movie2);
        movieSet2.add(movie3);

        movieSet3.add(movie3);
        movieSet3.add(movie1);

        wishList1 = WishListFactory.createWishlist(customer1, movieSet1);
        wishList2 = WishListFactory.createWishlist(customer2, movieSet2);
        wishList3 = WishListFactory.createWishlist(customer3, movieSet3);
    }

    @Test
    @Order(1)
    void create() {

        Wishlist createdWishlist1=wishlistService.create(wishList1);
        assertNotNull(createdWishlist1);
        System.out.println(createdWishlist1);

        Wishlist createdWishlist2=wishlistService.create(wishList2);
        assertNotNull(createdWishlist2);
        System.out.println(createdWishlist2);

        Wishlist createdWishlist3=wishlistService.create(wishList3);
        assertNotNull(createdWishlist3);
        System.out.println(createdWishlist3);
    }

    @Test
    @Order(2)
    void read() {

        Wishlist readWishlist=wishlistService.read(3L);
        assertNotNull(readWishlist);
        System.out.println(readWishlist);

    }

    @Test
    @Disabled
    @Order(3)
    void update() {

//        Wishlist wishlistToUpdate=new Wishlist.Builder().copy(wishList1).setShows(shows2).build();

    }

    @Test
    @Order(4)
    void delete() {
    }

    @Test
    @Order(5)
    void getAllWishlists() {

        List<Wishlist> allWishlists=wishlistService.getAllWishlists();
        assertNotNull(allWishlists);
        System.out.println(allWishlists);
    }
}