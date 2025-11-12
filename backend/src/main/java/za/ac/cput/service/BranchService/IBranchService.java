package za.ac.cput.service.BranchService;

import za.ac.cput.domain.Branch;
import za.ac.cput.service.IService;

import java.util.List;

public interface IBranchService extends IService <Branch,Long> {
    List<Branch> getAllBranches();
}
