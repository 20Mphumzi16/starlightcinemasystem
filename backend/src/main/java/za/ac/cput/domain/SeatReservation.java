package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class SeatReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne( optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne( optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    protected SeatReservation() {
    }

    private SeatReservation(SeatReservationBuilder builder) {
        this.id = builder.id;
        this.seat = builder.seat;
        this.booking = builder.booking;
        this.status = builder.status;
    }

    public Long getId() {
        return id;
    }

    public Seat getSeat() {
        return seat;
    }

    public Booking getBooking() {
        return booking;
    }

    public SeatStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatReservation)) return false;
        SeatReservation that = (SeatReservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SeatReservation{" +
                "id=" + id +
                ", seat=" + (seat ) +
                ", booking=" + (booking) +
                ", status=" + status +
                '}';
    }

    public static class SeatReservationBuilder {
        private Long id;
        private Seat seat;
        private Booking booking;
        private SeatStatus status;

        public SeatReservationBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public SeatReservationBuilder setSeat(Seat seat) {
            this.seat = seat;
            return this;
        }

        public SeatReservationBuilder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public SeatReservationBuilder setStatus(SeatStatus status) {
            this.status = status;
            return this;
        }

        public SeatReservationBuilder copy(SeatReservation seatReservation) {
            this.id = seatReservation.id;
            this.seat = seatReservation.seat;
            this.booking = seatReservation.booking;
            this.status = seatReservation.status;
            return this;
        }

        public SeatReservation build() {
            return new SeatReservation(this);
        }
    }
}
