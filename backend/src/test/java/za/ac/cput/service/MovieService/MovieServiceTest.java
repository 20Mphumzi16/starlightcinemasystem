package za.ac.cput.service.MovieService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Genre;
import za.ac.cput.domain.Movie;
import za.ac.cput.domain.ParentalRating;
import za.ac.cput.factory.MovieFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieServiceTest {

    private MovieService movieService;
    @Autowired
    public MovieServiceTest(MovieService movieService) {
        this.movieService = movieService;
    }


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



    @BeforeAll
    static void setUp() {

        genre1=new Genre.Builder().setId(1L).setName("Action").build();
        genre2=new Genre.Builder().setId(4L).setName("Comedy").build();
        genre3=new Genre.Builder().setId(14L).setName("Romance").build();

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


    }

    @Test

    void create() {
        Movie createdMovie1 = movieService.create(movie1);
        assertNotNull(createdMovie1);
        System.out.println(createdMovie1);

        Movie createdMovie2 = movieService.create(movie2);
        assertNotNull(createdMovie2);
        System.out.println(createdMovie2);

        Movie createdMovie3 = movieService.create(movie3);
        assertNotNull(createdMovie3);
        System.out.println(createdMovie3);
    }

    @Test
    void read() {

        Movie readMovie = movieService.read(1L);
        assertNotNull(readMovie);
        System.out.println(readMovie);
    }

    @Test
    void update() {
        Movie movieToUpdate = new Movie.MovieBuilder().copy(movie1).setDescription("Hola holaa movie is based on a south african gangster").build();
        Movie updatedMovie=movieService.update(movieToUpdate);
        assertNotNull(updatedMovie);
        System.out.println(updatedMovie);

    }

    @Test
    void delete() {
        boolean deleted=movieService.delete(3L);
        assertTrue(deleted);
        System.out.println("deleted");
    }

    @Test
    void getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        assertNotNull(movies);
        System.out.println(movies);
    }
}