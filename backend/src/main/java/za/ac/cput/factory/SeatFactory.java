package za.ac.cput.factory;

import za.ac.cput.domain.Hall;
import za.ac.cput.domain.Seat;
import za.ac.cput.util.Helper;

public class SeatFactory {
    public static Seat buildSeat(String seatNumber, Hall hall) {
        if(Helper.isStringNullorEmpty(seatNumber)||Helper.isObjectNull(hall)){
            return null;
        }
        return new Seat.SeatBuilder()
                .setSeatNumber(seatNumber)
                .setHall(hall)
                .build();
    }
}
