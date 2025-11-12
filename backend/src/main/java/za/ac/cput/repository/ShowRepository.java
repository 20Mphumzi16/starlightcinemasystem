package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Show;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findAllByMovie_Id(Long id);
}
