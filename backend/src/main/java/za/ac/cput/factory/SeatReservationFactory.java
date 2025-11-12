package za.ac.cput.factory;

import za.ac.cput.domain.Booking;
import za.ac.cput.domain.Seat;
import za.ac.cput.domain.SeatReservation;
import za.ac.cput.domain.SeatStatus;
import za.ac.cput.util.Helper;

public class SeatReservationFactory {

    public static SeatReservation createSeatReservation(Seat seat, Booking booking, SeatStatus seatStatus) {
        if(Helper.isObjectNull(seat)
                ||Helper.isObjectNull(booking)
                ||Helper.isStringNullorEmpty(seatStatus.toString())){
            return null;
        }
        return new SeatReservation.SeatReservationBuilder()
                .setSeat(seat)
                .setBooking(booking)
                .setStatus(seatStatus)
                .build();

    }
}
