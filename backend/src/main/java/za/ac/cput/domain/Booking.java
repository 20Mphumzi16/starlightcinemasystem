package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne( optional = false )
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne( optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Customer customer;
    @JsonIgnore
    @OneToMany(mappedBy = "booking", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<SeatReservation> reservedSeats = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private double totalPrice;

    private LocalDateTime createdAt;


    protected Booking() {

    }

    private Booking(BookingBuilder builder) {
        this.id = builder.id;
        this.show = builder.show;
        this.customer = builder.customer;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.totalPrice=builder.totalPrice;
    }

    public Long getId() {
        return id;
    }

    public Show getShow() {
        return show;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<SeatReservation> getReservedSeats() {
        return reservedSeats;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Double.compare(totalPrice, booking.totalPrice) == 0 && Objects.equals(id, booking.id) && Objects.equals(show, booking.show) && Objects.equals(customer, booking.customer) && Objects.equals(reservedSeats, booking.reservedSeats) && status == booking.status && Objects.equals(createdAt, booking.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, show, customer, reservedSeats, status, totalPrice, createdAt);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", show=" + show +
                ", customer=" + customer +
                ", reservedSeats=" + reservedSeats +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class BookingBuilder {
        private Long id;
        private Show show;
        private Customer customer;
        private BookingStatus status;
        private double totalPrice;
        private LocalDateTime createdAt;

        public BookingBuilder() {
        }

        public BookingBuilder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public BookingBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BookingBuilder setShow(Show show) {
            this.show = show;
            return this;
        }

        public BookingBuilder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public BookingBuilder setStatus(BookingStatus status) {
            this.status = status;
            return this;
        }

        public BookingBuilder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BookingBuilder copy(Booking booking){
            this.id=booking.id;
            this.show=booking.show;
            this.customer=booking.customer;
            this.status=booking.status;
            this.totalPrice=booking.totalPrice;
            this.createdAt=booking.createdAt;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}
