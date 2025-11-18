package za.ac.cput.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Admin;
import za.ac.cput.repository.AdminRepository;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(Long aLong) {
        return adminRepository.findById(aLong).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public boolean delete(Long aLong) {
         adminRepository.deleteById(aLong);
        return !adminRepository.existsById(aLong);
    }

    public Admin getBYEmailAndPassword(String email, String password) {
        return adminRepository.findByEmail_AndPassword(email, password);
    }
}
