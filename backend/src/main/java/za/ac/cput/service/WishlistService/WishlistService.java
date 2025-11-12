package za.ac.cput.service.WishlistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.repository.WishlistRepository;

import java.util.List;

@Service
public class WishlistService implements IWishlistService {

    private WishlistRepository wishlistRepository;
    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Wishlist create(Wishlist wishlist) {
       return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist read(Long aLong) {
       return wishlistRepository.findById(aLong).orElse(null);
    }

    @Override
    public Wishlist update(Wishlist wishlist) {
       return wishlistRepository.save(wishlist);
    }

    @Override
    public boolean delete(Long aLong) {
        wishlistRepository.deleteById(aLong);
        return !wishlistRepository.existsById(aLong);
    }

    @Override
    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public int calculateQuantity(Long wishListId) {
        return wishlistRepository.numberOfMoviesOnThisWishlist(wishListId);
    }

    public Wishlist getCustomerWishlist(Long id) {
        return wishlistRepository.findByCustomer_Id(id);
    }


}
