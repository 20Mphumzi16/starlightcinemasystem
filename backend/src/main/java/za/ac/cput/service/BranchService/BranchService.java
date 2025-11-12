package za.ac.cput.service.BranchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Branch;
import za.ac.cput.repository.BranchRepository;
import za.ac.cput.service.IService;

import java.util.List;

@Service
public class BranchService implements IBranchService {

    private BranchRepository branchRepository;
    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch create(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Branch read(Long id) {
        return branchRepository.findById(id).orElse(null);
    }

    @Override
    public Branch update(Branch branch) {
        return branchRepository.save(branch);

    }

    @Override
    public boolean delete(Long id) {
        branchRepository.deleteById(id);
        return !branchRepository.existsById(id);
    }



    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }
}
