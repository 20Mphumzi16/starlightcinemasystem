package za.ac.cput.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Embedded
    protected Name name;
    @Lob()
    @Column(length=100000)
    protected byte[] photo;
    @ManyToOne
    protected Gender gender;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected LocalDate dateOfBirth;
    @ManyToMany()
    @JoinTable(

            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected Set<Role> roles;

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.deepEquals(photo, user.photo) && Objects.equals(gender, user.gender) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(dateOfBirth, user.dateOfBirth) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, Arrays.hashCode(photo), gender, email, password, phoneNumber, dateOfBirth, roles);
    }
}
