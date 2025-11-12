package za.ac.cput.service.RoleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Role;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class RoleServiceTest {

    private RoleService roleService;

    private Role role;
    private Role role2;

    @Autowired
    public RoleServiceTest(RoleService roleService) {
        this.roleService = roleService;
    }

    @BeforeEach
    void setUp() {

        role = new Role("Customer");
        role2 = new Role("Admin");


    }

    @Test
    void create() {
        Role createdRole1=roleService.create(role);
        assertNotNull(createdRole1);
        System.out.println(createdRole1);

        Role createdRole2=roleService.create(role2);
        assertNotNull(createdRole2);
        System.out.println(createdRole2);
    }
}