package za.ac.cput.factory;

import za.ac.cput.domain.Hall;
import za.ac.cput.domain.Movie;
import za.ac.cput.domain.Show;
import za.ac.cput.domain.ShowStatus;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

public class ShowFactory {

    //Times for start and end will have test conditions later

    public static Show createShow(Movie movie, Hall hall, LocalDateTime startTime, LocalDateTime endTime, double price, ShowStatus showStatus) {

        if(Helper.isObjectNull(movie)||Helper.isObjectNull(hall)||price<=0||Helper.isObjectNull(startTime)||Helper.isObjectNull(endTime)||Helper.isStringNullorEmpty(showStatus.toString())){
            return null;
        }
        return new Show.ShowBuilder()
                .setMovie(movie)
                .setHall(hall)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setPrice(price)
                .setStatus(showStatus)
                .build();
    }

}
