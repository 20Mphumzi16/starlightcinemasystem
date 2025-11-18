package za.ac.cput.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
@Entity
@DiscriminatorValue("Customer")
public class Customer extends User{

    protected Customer(CustomerBuilder builder){
        super.id=builder.id;
        super.name=builder.name;
        super.photo=builder.photo;
        super.gender=builder.gender;
        super.email=builder.email;
        super.password=builder.password;
        super.phoneNumber=builder.phoneNumber;
        super.dateOfBirth=builder.dateOfBirth;
        super.roles=builder.roles;
    }

    public Customer(){

    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name=" + name +
                ", photo=" + Arrays.toString(photo) +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", roles=" + roles +
                '}';
    }


    public static class CustomerBuilder{
        private Long id;
        private Name name;
        private byte[] photo;
        private Gender gender;
        private String email;
        private String password;
        private String phoneNumber;
        private LocalDate dateOfBirth;
        private Set<Role> roles;

        public CustomerBuilder() {
        }

        public CustomerBuilder setId(Long id){
            this.id = id;
            return this;
        }

        public CustomerBuilder setName(Name name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder setPhoto(byte [] photo) {
            this.photo = photo;
            return this;
        }

        public CustomerBuilder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public CustomerBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public CustomerBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerBuilder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public CustomerBuilder setRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }
        public CustomerBuilder copy(Customer c){
            this.id=c.id;
            this.name=c.name;
            this.photo=c.photo;
            this.gender=c.gender;
            this.email=c.email;
            this.password=c.password;
            this.phoneNumber=c.phoneNumber;
            this.dateOfBirth=c.dateOfBirth;
            this.roles=c.roles;
            return this;
        }
        public Customer build(){
            return new Customer(this);
        }
    }
}
