package za.ac.cput.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;


import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
public class Wishlist{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade =CascadeType.ALL)
    private Customer customer;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "wishList_movies",
            joinColumns = @JoinColumn(name = "wishList_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @Nullable
    private Set<Movie> movieSet;


    public Wishlist() {
    }
    protected Wishlist(Builder builder){
        this.id=builder.id;
        this.customer=builder.customer;
        this.movieSet=builder.movieSet;
    }

    public Long getId() {
        return id;
    }


    public Customer getCustomer() {
        return customer;
    }

    public Set<Movie> getMovieSet() {
        return movieSet;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wishlist)) return false;
        Wishlist that = (Wishlist) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "WishList{" +
                "id=" + id +
                ", customer=" + customer +
                ", movieSet=" + movieSet +
                '}';
    }

    public static class Builder{

        private Long id;
        private Customer customer;
        private Set<Movie>movieSet;

        public Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }



        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setMovies(Set<Movie> movieSet) {
            this.movieSet = movieSet;
            return this;
        }

        public Builder copy(Wishlist wishList){
            this.id=wishList.id;
            this.customer=wishList.customer;
            this.movieSet=wishList.movieSet;
            return this;
        }
        public Wishlist build(){
            return new Wishlist(this);
        }
    }
}
