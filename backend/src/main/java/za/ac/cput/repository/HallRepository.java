package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Hall;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall, Long> {
    List<Hall> findAllByBranch_Id(Long id);

}
