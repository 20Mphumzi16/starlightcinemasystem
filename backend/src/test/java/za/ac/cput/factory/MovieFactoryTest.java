package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Genre;
import za.ac.cput.domain.Movie;
import za.ac.cput.domain.ParentalRating;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieFactoryTest {

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

    @BeforeAll
    static void setUp() {

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

        movie1=MovieFactory.createMovie("Fantastic 4",new byte[0], LocalDate.of(2024,6,20),genres1,"www.trailer.com","www.image.com",160,rating1,"The fantstic 4 is back on track to save the world.");
        movie2=MovieFactory.createMovie("Expandables",new byte[0],LocalDate.of(2025,1,20),genres2,"www.trailer.com","www.image.com",92,rating2,"Dom is kidnapped and the Expandables are born again.");
        movie3=MovieFactory.createMovie("Love Island",new byte[0],LocalDate.of(2024,2,17),genres3,"www.trailer.com","www.image.com",90,rating3,"TLove is in the air in this city.");


    }

    @Test
    void createMovie() {

        assertNotNull(movie1);
        assertNotNull(movie2);
        assertNotNull(movie3);

        System.out.println(movie1);
        System.out.println(movie2);
        System.out.println(movie3);
    }
}