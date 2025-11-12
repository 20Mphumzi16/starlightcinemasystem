package za.ac.cput.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

public class Name implements ValueObject<Name> {
    private String firstName;
    private String middleName;
    private String lastName;

    public Name() {
    }

    public Name(NameBuilder builder) {
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) && Objects.equals(middleName, name.middleName) && Objects.equals(lastName, name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName);
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean sameValueAs(Name other) {
        return other != null &&
                Objects.equals(firstName, other.firstName) &&
                Objects.equals(middleName, other.middleName) &&
                Objects.equals(lastName, other.lastName);
    }

    public static class NameBuilder {
        private String firstName;
        private String middleName;
        private String lastName;

        public NameBuilder() {
        }

        public NameBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public NameBuilder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public NameBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public NameBuilder copy(Name name) {
            this.firstName = name.firstName;
            this.middleName = name.middleName;
            this.lastName = name.lastName;
            return this;
        }


        public Name build() {
            return new Name(this);
        }
    }
}
