package za.ac.cput.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // "A5", "B7"

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    public Seat() {

    }
    public Seat(SeatBuilder builder) {
        this.id=builder.id;
        this.seatNumber=builder.seatNumber;
        this.hall=builder.hall;
    }

    public Long getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Hall getHall() {
        return hall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id) && Objects.equals(seatNumber, seat.seatNumber) && Objects.equals(hall, seat.hall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seatNumber, hall);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatNumber='" + seatNumber + '\'' +
                ", hall=" + hall +
                '}';
    }

    public static class SeatBuilder {
        private Long id;
        private String seatNumber;
        private Hall hall;

        public SeatBuilder () {}

        public SeatBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public SeatBuilder setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }

        public SeatBuilder setHall(Hall hall) {
            this.hall = hall;
            return this;
        }

        public SeatBuilder copy(Seat seat) {
            this.id = seat.id;
            this.seatNumber = seat.seatNumber;
            this.hall = seat.hall;
            return this;
        }
        public Seat build() {
            return new Seat(this);
        }
    }



}
