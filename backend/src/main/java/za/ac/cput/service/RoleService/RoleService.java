package za.ac.cput.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Role;
import za.ac.cput.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
       return roleRepository.findAll();
    }

    @Override
    public Role create(Role role) {
     return roleRepository.save(role);
    }

    @Override
    public Role read(Long aLong) {
  return roleRepository.findById(aLong).orElse(null);
    }

    @Override
    public Role update(Role role) {
      return roleRepository.save(role);
    }

    @Override
    public boolean delete(Long aLong) {
       roleRepository.deleteById(aLong);
       return !roleRepository.existsById(aLong);
    }
}
