package za.ac.cput.factory;

import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Gender;
import za.ac.cput.domain.Name;
import za.ac.cput.domain.Role;
import za.ac.cput.util.Helper;

import java.time.LocalDate;
import java.util.Set;

public class CustomerFactory {

    public static Customer createCustomer(String firstName, String lastName, byte[]photo, Gender gender, String email, String password, String phoneNumber, LocalDate dob, Set<Role>roles) {
        if(Helper.isStringNullorEmpty(firstName)
                ||Helper.isStringNullorEmpty(lastName)
                ||Helper.isArrayNullOrEmpty(photo)
                ||Helper.isObjectNull(gender)
                ||Helper.isEmailValid(email)
                ||Helper.isStringNullorEmpty(password)
                ||Helper.isStringNullorEmpty(phoneNumber)
                ||Helper.isValidDate(dob)
                ||Helper.isSetNullorEmpty(roles)


        ) {
            return null;
        }

        Name name =new Name.NameBuilder().setFirstName(firstName).setLastName(lastName).build();

        return new Customer.CustomerBuilder()
                .setName(name)
                .setPhoto(photo)
                .setGender(gender)
                .setEmail(email)
                .setPassword(password)
                .setDateOfBirth(dob)
                .setPhoneNumber(phoneNumber)
                .setRoles(roles)
                .build();
    }

    public static Customer createCustomer(String firstName,String middleName, String lastName, byte[]photo, Gender gender, String email, String password, String phoneNumber, LocalDate dob, Set<Role>roles) {
        if(Helper.isStringNullorEmpty(firstName)
                ||Helper.isStringNullorEmpty(lastName)
                ||Helper.isArrayNullOrEmpty(photo)
                ||Helper.isObjectNull(gender)
                ||Helper.isEmailValid(email)
                ||Helper.isStringNullorEmpty(password)
                ||Helper.isStringNullorEmpty(phoneNumber)
                ||Helper.isValidDate(dob)
                ||Helper.isSetNullorEmpty(roles)


        ) {
            return null;
        }

        Name name =new Name.NameBuilder().setFirstName(firstName).setMiddleName(middleName).setLastName(lastName).build();

        return new Customer.CustomerBuilder()
                .setName(name)
                .setPhoto(photo)
                .setGender(gender)
                .setEmail(email)
                .setPassword(password)
                .setDateOfBirth(dob)
                .setPhoneNumber(phoneNumber)
                .setRoles(roles)
                .build();
    }
}
