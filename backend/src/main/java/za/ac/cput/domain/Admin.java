package za.ac.cput.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {

    protected Admin(AdminBuilder builder){
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

    public Admin(){

    }

    @Override
    public String toString() {
        return "Admin{" +
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


    public static class AdminBuilder{
        private Long id;
        private Name name;
        private byte[] photo;
        private Gender gender;
        private String email;
        private String password;
        private String phoneNumber;
        private LocalDate dateOfBirth;
        private Set<Role> roles;

        public AdminBuilder() {
        }

        public AdminBuilder setId(Long id){
            this.id = id;
            return this;
        }

        public AdminBuilder setName(Name name) {
            this.name = name;
            return this;
        }

        public AdminBuilder setPhoto(byte [] photo) {
            this.photo = photo;
            return this;
        }
        public AdminBuilder setGender(Gender gender){
            this.gender = gender;
            return this;
        }

        public AdminBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AdminBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AdminBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public AdminBuilder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public AdminBuilder setRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }
        public AdminBuilder copy(Admin admin){
            this.id=admin.id;
            this.name=admin.name;
            this.photo=admin.photo;
            this.email=admin.email;
            this.password=admin.password;
            this.phoneNumber=admin.phoneNumber;
            this.dateOfBirth=admin.dateOfBirth;
            this.roles=admin.roles;
            return this;
        }
        public Admin build(){
            return new Admin(this);
        }
    }
}
