package za.ac.cput.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Column(length = 100000)
    private byte[] photo;
    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "genre")
    private Set<Genre> genres;
    private LocalDate releaseDate;
    private int durationMinutes;
    @ManyToOne(fetch = FetchType.EAGER)
    private ParentalRating rating;
    private String description;

    public Movie() {
    }

    public Movie(MovieBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.photo = builder.photo;
        this.releaseDate=builder.releaseDate;
        this.genres = builder.genres;
        this.durationMinutes = builder.durationMinutes;
        this.rating = builder.rating;
        this.description = builder.description;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public ParentalRating getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", genres=" + genres +
                ", releaseDate=" + releaseDate +
                ", durationMinutes=" + durationMinutes +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                '}';
    }

    public static class MovieBuilder {
        private Long id;
        private String title;
        private byte[] photo;
        private LocalDate releaseDate;
        private Set<Genre> genres;
        private int durationMinutes;
        private ParentalRating rating;
        private String description;

        public MovieBuilder() {

        }

        public MovieBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public MovieBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder setPhoto(byte[] photo) {
            this.photo = photo;
            return this;
        }

        public MovieBuilder setReleaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public MovieBuilder setGenres(Set<Genre> genres) {
            this.genres = genres;
            return this;
        }

        public MovieBuilder setDurationMinutes(int durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public MovieBuilder setRating(ParentalRating rating) {
            this.rating = rating;
            return this;
        }

        public MovieBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public MovieBuilder copy(Movie movie) {
            this.id = movie.id;
            this.title = movie.title;
            this.photo = movie.photo;
            this.releaseDate = movie.releaseDate;
            this.genres = movie.genres;
            this.durationMinutes = movie.durationMinutes;
            this.rating = movie.rating;
            this.description = movie.description;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
