package za.ac.cput.factory;

import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class BookingFactory {
    public static Booking createBooking(Show show, Customer customer, BookingStatus bookingStatus, LocalDateTime bookingDate,double totalPrice) {

        if(Helper.isObjectNull(show)||Helper.isObjectNull(customer)||Helper.isStringNullorEmpty(bookingStatus.toString())||Helper.isFutureDateTime(bookingDate)|| totalPrice <= 0){
            return null;
        }

        return new Booking.BookingBuilder()
                .setShow(show)
                .setCustomer(customer)
                .setStatus(bookingStatus)
                .setCreatedAt(bookingDate)
                .setTotalPrice(totalPrice)
                .build();


    }
}

