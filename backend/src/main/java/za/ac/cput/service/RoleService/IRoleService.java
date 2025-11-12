package za.ac.cput.service.RoleService;

import za.ac.cput.domain.Role;
import za.ac.cput.service.IService;

import java.util.List;

public interface IRoleService extends IService<Role,Long> {
    List<Role> getAllRoles();
}
