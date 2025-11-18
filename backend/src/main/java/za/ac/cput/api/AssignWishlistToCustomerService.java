package za.ac.cput.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.service.CustomerService.CustomerService;
import za.ac.cput.service.WishlistService.WishlistService;


@Component
public class AssignWishlistToCustomerService {

    private CustomerService customerService;
    private WishlistService wishlistService;

    @Autowired
    public AssignWishlistToCustomerService(CustomerService customerService, WishlistService wishlistService) {
        this.customerService = customerService;
        this.wishlistService = wishlistService;
    }

    public Wishlist assignWishlistToCustomer(Long customerId) {

        if (customerId > 0) {

            Customer customer = customerService.read(customerId);

            if (customer != null) {

                Wishlist newWishlist = new Wishlist.Builder()
                        .setCustomer(customer)
                        .build();

                Wishlist assignedWishList = wishlistService.create(newWishlist);
                System.out.println("Assigned WishList" + assignedWishList);

                return assignedWishList;
            }
            throw new NullPointerException(" No Customer for specified Id" + customerId);
        }
        throw new IllegalArgumentException("Invalid Customer Id " + customerId);
    }

}
