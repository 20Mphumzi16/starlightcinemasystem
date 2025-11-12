package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}
