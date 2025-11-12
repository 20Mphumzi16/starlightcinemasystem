package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class ParentalRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rating;
    private String description;

    public ParentalRating() {
    }

    public ParentalRating(ParentalRatingBuilder parentalRatingBuilder) {
        this.id=parentalRatingBuilder.id;
        this.rating=parentalRatingBuilder.rating;
        this.description=parentalRatingBuilder.description;
    }

    public Long getId() {
        return id;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentalRating that = (ParentalRating) o;
        return Objects.equals(id, that.id) && Objects.equals(rating, that.rating) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, description);
    }

    @Override
    public String toString() {
        return "ParentalRating{" +
                "id=" + id +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    public static class ParentalRatingBuilder {
        private Long id;
        private String rating;
        private String description;
        public ParentalRatingBuilder() {

        }

        public ParentalRatingBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ParentalRatingBuilder setRating(String rating) {
            this.rating = rating;
            return this;
        }

        public ParentalRatingBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ParentalRating build() {
            return new ParentalRating(this);
        }
    }
}
