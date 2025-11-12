package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Gender;
import za.ac.cput.domain.Role;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFactoryTest {

    private static Customer customer1;
    private static Customer customer2;
    private static Customer customer3;

    private static Gender gender1;
    private static Gender gender2;
    private static Gender gender3;

    private static Role role1;
    private static Role role2;
    private static Role role3;

    private static Set<Role>roles1;


    @BeforeAll
    static void setUp() {
        System.out.println("=====================================SETUP===============================================");

        role1=new Role("Customer");


        Set<Role>roles1=new HashSet<>();
        roles1.add(role1);


        gender1=new Gender.GenderBuilder().setName("Male").build();
        gender2=new Gender.GenderBuilder().setName("Female").build();
        gender3=new Gender.GenderBuilder().setName("Other").build();

        customer1 = CustomerFactory.createCustomer("Mpumzi","Mbula",new byte[0],gender1,"mphumzimbula@gmail.com","20Mphmbu16!","0658436358", LocalDate.of(2000,6,20),roles1);
        customer2 = CustomerFactory.createCustomer("Vuyokazi","Xayiya",new byte[0],gender2,"vuyokazixayiya@gmail.com","20VeeYiya16!","0835805117", LocalDate.of(2000,1,14),roles1);
        customer3 = CustomerFactory.createCustomer("Nombulelo","Pretty","Mbula",new byte[0],gender2,"buliembula@gmail.com","Bulie123!","0618227474", LocalDate.of(2002,5,17),roles1);


    }

    @Test
    void createCustomer() {


        assertNotNull(customer1);

        assertNotNull(customer2);

        System.out.println(customer1);
        System.out.println(customer2);

    }

    @Test
    void testCreateCustomer() {
        assertNotNull(customer3);
        System.out.println(customer3);
    }
}