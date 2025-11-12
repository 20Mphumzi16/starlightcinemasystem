package za.ac.cput.factory;

import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Movie;
import za.ac.cput.domain.Show;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.util.Helper;

import java.util.Set;

public class WishListFactory {

    public static Wishlist createWishlist(Customer customer, Set<Movie> movieSet) {
       if( Helper.isObjectNull(customer))
           return null;
        return new Wishlist.Builder().setCustomer(customer).setMovies(movieSet).build();
    }

}
