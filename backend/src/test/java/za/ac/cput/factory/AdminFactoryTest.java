package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Gender;
import za.ac.cput.domain.Role;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdminFactoryTest {
    private static Admin admin1;
    private static Admin admin2;
    private static Admin admin3;


    private static Gender gender1;
    private static Gender gender2;
    private static Gender gender3;

    private static Role role1;
    private static Role role2;
    private static Role role3;

    private static Set<Role> roles1;


    @BeforeAll
    static void setUp() {

        System.out.println("=====================================SETUP===============================================");


        role1 = new Role("Admin");

        Set<Role> roles1 = new HashSet<>();
        roles1.add(role1);

        gender1 = new Gender.GenderBuilder().setName("Male").build();
        gender2 = new Gender.GenderBuilder().setName("Female").build();
        gender3 = new Gender.GenderBuilder().setName("Other").build();

        admin1 = AdminFactory.createAdmin("Mpumzi", "Mbula", new byte[0], gender1, "mphumzimbula@gmail.com", "20Mphmbu16!", "0658436358", LocalDate.of(2000, 6, 20), roles1);
        admin2 = AdminFactory.createAdmin("Vuyokazi", "Xayiya", new byte[0], gender2, "vuyokazixayiya@gmail.com", "20VeeYiya16!", "0835805117", LocalDate.of(2000, 1, 14), roles1);
        admin3 = AdminFactory.createAdmin("Nombulelo", "Pretty", "Mbula", new byte[0], gender2, "buliembula@gmail.com", "Bulie123!", "0618227474", LocalDate.of(2002, 5, 17), roles1);


    }

    @Test
    void createAdmin() {
        assertNotNull(admin1);
        assertNotNull(admin2);
        System.out.println(admin1);
        System.out.println(admin2);
    }

    @Test
    void testCreateAdmin() {
        assertNotNull(admin3);
        System.out.println(admin3);
    }
}