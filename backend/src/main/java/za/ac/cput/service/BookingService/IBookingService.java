package za.ac.cput.service.BookingService;

import za.ac.cput.domain.Booking;
import za.ac.cput.service.IService;

import java.util.List;

public interface IBookingService extends IService<Booking,Long> {

    List<Booking>getAllBookings();
}
