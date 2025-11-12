package za.ac.cput.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import za.ac.cput.domain.Movie;
import za.ac.cput.domain.Show;

import za.ac.cput.domain.Wishlist;
import za.ac.cput.service.MovieService.MovieService;
import za.ac.cput.service.ShowService.ShowService;
import za.ac.cput.service.WishlistService.WishlistService;


import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Component
public class RemoveFromWishlistService {
    private final MovieService  movieService;
    private final WishlistService wishlistService;

    @Autowired
    public RemoveFromWishlistService(WishlistService wishlistService, MovieService movieService) {
        this.wishlistService=wishlistService;
       this.movieService=movieService;
    }

    public Wishlist removeShowFromWishList(Long wishlistId, Long movieId) {
        // Retrieve the wishList
        Wishlist wishlist = wishlistService.read(wishlistId);
        if (wishlist == null) {
            // Handle wishList not found case
            System.out.println("WishList not found with ID: " + wishlistId);
            return null;
        }

        System.out.println("Before removing from wishList operation: " + wishlist);

        // Flag to check if the comic book was found and removed
        boolean updated = false;

        // Use Iterator to safely remove items from the list
        assert wishlist.getMovieSet() != null;
        Iterator<Movie> iterator = wishlist.getMovieSet().iterator();
        while (iterator.hasNext()) {
        Movie movie1 = iterator.next();
            if (Objects.equals(movie1.getId(), movieId)) {
                System.out.println("Removing Show with id: " + movie1.getId());
                iterator.remove(); // Remove the movie  from the wishList
                updated = true;
                break;
            }
        }

        if (!updated) {
            // Handle case where movie was not found in the cart
            System.out.println("movie with Id: " + movieId + " not found in wishlist.");
            return wishlist; // Return the cart as is
        }

        // Create a new Cart instance with updated date
        Wishlist updatedWishlist = new Wishlist.Builder()
                .copy(wishlist)
                .build();

        // Update the cart in the service
        return wishlistService.update(updatedWishlist);
    }
}
