package za.ac.cput.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Movie;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.service.MovieService.MovieService;
import za.ac.cput.service.WishlistService.WishlistService;

import java.util.HashSet;

@Component
public class AddToWishlistService {

  private final MovieService movieService;
    private final WishlistService wishlistService;

    @Autowired
    public AddToWishlistService(WishlistService wishListService, MovieService movieService) {
        this.wishlistService = wishListService;
        this.movieService = movieService;
    }

    public Wishlist addMovieToWishlist(Long wishlistId, Long movieId) {
        Wishlist wishlist = wishlistService.read(wishlistId);
        Movie movie = movieService.read(movieId);

        if (wishlist == null || movie == null) {
            throw new IllegalArgumentException("Wishlist or movie not found");
        }

        boolean alreadyInWishlist = wishlist.getMovieSet().stream()
                .anyMatch(m -> m.getId().equals(movieId));

        if (alreadyInWishlist) {
            System.out.println("Movie already in wishlist");
            return wishlist;
        }

        wishlist.getMovieSet().add(movie);
        System.out.println("Movie added to wishlist: " + movie);

        return wishlistService.update(wishlist);
    }




}
