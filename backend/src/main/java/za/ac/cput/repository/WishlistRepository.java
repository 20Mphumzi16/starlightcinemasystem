package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.ac.cput.domain.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Wishlist findByCustomer_Id(Long id);
    @Query("SELECT COUNT(cb) FROM Wishlist w JOIN w.movieSet cb WHERE w.id = :id")
    int numberOfMoviesOnThisWishlist(@Param("id") Long id);
}
