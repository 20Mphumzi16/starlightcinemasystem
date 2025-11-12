package za.ac.cput.factory;

import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;

import java.time.LocalDate;
import java.util.Set;

public class AdminFactory {

    public static Admin createAdmin(String firstName, String lastName, byte[]photo, Gender gender, String email, String password, String phoneNumber, LocalDate dob, Set<Role>roles) {
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

        return new Admin.AdminBuilder()
                .setName(name)
                .setPhoto(photo)
                .setGender(gender)
                .setEmail(email)
                .setPassword(password)
                .setDateOfBirth(dob)
                .setPhoneNumber(phoneNumber).build();
    }

    public static Admin createAdmin(String firstName,String middleName, String lastName, byte[]photo, Gender gender, String email, String password, String phoneNumber, LocalDate dob, Set<Role>roles) {
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

        return new Admin.AdminBuilder()
                .setName(name)
                .setPhoto(photo)
                .setGender(gender)
                .setEmail(email)
                .setPassword(password)
                .setDateOfBirth(dob)
                .setPhoneNumber(phoneNumber).build();
    }
}
