package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat>findAllByHall_Id(Long id);

}
