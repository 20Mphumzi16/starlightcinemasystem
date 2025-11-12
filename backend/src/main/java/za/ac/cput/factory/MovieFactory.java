package za.ac.cput.factory;

import za.ac.cput.domain.Genre;
import za.ac.cput.domain.Movie;
import za.ac.cput.domain.ParentalRating;
import za.ac.cput.util.Helper;

import java.time.LocalDate;
import java.util.Set;

public class MovieFactory {
    public static Movie createMovie(String title, byte[]photo, LocalDate releaseDate, Set<Genre>genres, int durationMinutes, ParentalRating rating, String description) {

        if(Helper.isStringNullorEmpty(title)
                ||Helper.isArrayNullOrEmpty(photo)
                ||Helper.isSetNullorEmpty(genres)
                ||durationMinutes<=0
                ||Helper.isObjectNull(rating)
                ||Helper.isStringNullorEmpty(description)

        )
        {
            return null;
        }

        return new Movie.MovieBuilder()
                .setTitle(title)
                .setPhoto(photo)
                .setGenres(genres)
                .setReleaseDate(releaseDate)
                .setDurationMinutes(durationMinutes)
                .setDescription(description)
                .setRating(rating)
                .build();
    }
}
