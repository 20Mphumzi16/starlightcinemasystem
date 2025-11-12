package za.ac.cput.controller;

import org.springframework.web.bind.annotation.*;
import za.ac.cput.api.AddToWishlistService;
import za.ac.cput.api.AssignWishlistToCustomerService;
import za.ac.cput.api.RemoveFromWishlistService;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.repository.WishlistRepository;
import za.ac.cput.service.WishlistService.WishlistService;

import java.util.List;

@CrossOrigin("localhost:3000")
@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final AssignWishlistToCustomerService assignWishlistToCustomerService;
    private final AddToWishlistService addToWishlistService;
    private final RemoveFromWishlistService removeFromWishlistService;

    public WishlistController(WishlistService wishlistService, AssignWishlistToCustomerService assignWishlistToCustomerService, AddToWishlistService addToWishlistService, RemoveFromWishlistService removeFromWishlistService) {
        this.wishlistService = wishlistService;
        this.assignWishlistToCustomerService = assignWishlistToCustomerService;
        this.addToWishlistService = addToWishlistService;
        this.removeFromWishlistService = removeFromWishlistService;
    }

    @PostMapping("/create")
    public Wishlist create(@RequestBody Wishlist wishlist) {
        return wishlistService.create(wishlist);
    }

    @GetMapping("/read/{id}")
    public Wishlist read(@PathVariable Long id) {
        return wishlistService.read(id);
    }

    @PostMapping("/update")
    public Wishlist update(@RequestBody Wishlist wishlist) {
        return wishlistService.update(wishlist);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return wishlistService.delete(id);
    }

    @GetMapping("/get-all")
    public List<Wishlist> getAll() {
        return wishlistService.getAllWishlists();
    }

    @PostMapping("/assignWishlist/{customerId}")
    public Wishlist assignWishlist(@PathVariable Long customerId) {
        return assignWishlistToCustomerService.assignWishlistToCustomer(customerId);
    }

    @PostMapping("/add-to-wishlist/{wishlistId}/{movieId}")
    public Wishlist addToWishlist(@PathVariable Long wishlistId, @PathVariable Long movieId) {
        return addToWishlistService.addMovieToWishlist(wishlistId, movieId);
    }

    @PostMapping("/remove-from-wishlist/{wishlistId}/{movieId}")
    public Wishlist removeFromWishlist(@PathVariable Long wishlistId, @PathVariable Long movieId) {
        return removeFromWishlistService.removeShowFromWishList(wishlistId, movieId);
    }

    @GetMapping("/user-wishlist/{id}")
    public Wishlist getCustomerWishlist(@PathVariable Long id) {
        return wishlistService.getCustomerWishlist(id);
    }



}
