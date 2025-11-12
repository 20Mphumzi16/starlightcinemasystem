package za.ac.cput.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private Movie movie;
    @ManyToOne()
    private Hall hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private ShowStatus status;

    public Show(ShowBuilder builder) {
        this.id = builder.id;
        this.movie = builder.movie;
        this.hall = builder.hall;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.price = builder.price;
        this.status = builder.status;

    }

    public Show() {
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Hall getHall() {
        return hall;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getPrice() {
        return price;
    }

    public ShowStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Double.compare(price, show.price) == 0 && Objects.equals(id, show.id) && Objects.equals(movie, show.movie) && Objects.equals(hall, show.hall) && Objects.equals(startTime, show.startTime) && Objects.equals(endTime, show.endTime) && status == show.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, hall, startTime, endTime, price, status);
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", movie=" + movie +
                ", hall=" + hall +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", price=" + price +
                ", status=" + status +
                '}';
    }

    public static class ShowBuilder {
        private Long id;
        private Movie movie;
        private Hall hall;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private double price;
        private ShowStatus status;

        public ShowBuilder() {
        }

        public ShowBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ShowBuilder setMovie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public ShowBuilder setHall(Hall hall) {
            this.hall = hall;
            return this;
        }

        public ShowBuilder setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public ShowBuilder setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public ShowBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public ShowBuilder setStatus(ShowStatus status) {
            this.status = status;
            return this;
        }

        public ShowBuilder copy(Show show){
            this.id = show.id;
            this.movie = show.movie;
            this.hall = show.hall;
            this.startTime = show.startTime;
            this.endTime = show.endTime;
            this.price = show.price;
            this.status = show.status;
            return this;
        }
        public Show build() {
            return new Show(this);
        }
    }
}
