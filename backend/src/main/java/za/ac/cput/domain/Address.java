package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String suburb;
    private String city;
    private String postalCode;

    public Address() {
    }

    public Address(AddressBuilder builder){
        this.id=builder.id;
        this.street=builder.street;
        this.suburb=builder.suburb;
        this.city=builder.city;
        this.postalCode=builder.postalCode;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return postalCode == address.postalCode && Objects.equals(id, address.id) && Objects.equals(street, address.street) && Objects.equals(suburb, address.suburb) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, suburb, city, postalCode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", suburb='" + suburb + '\'' +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }
    public static class AddressBuilder {
        private Long id;
        private String street;
        private String suburb;
        private String city;
        private String postalCode;

        public AddressBuilder() {
        }

        public AddressBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public AddressBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder setSuburb(String suburb) {
            this.suburb = suburb;
            return this;
        }

        public AddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }
        public AddressBuilder copy(Address address){
            this.id=address.id;
            this.street=address.street;
            this.suburb=address.suburb;
            this.city=address.city;
            this.postalCode=address.postalCode;
            return this;
        }

        public Address build(){
            return new Address(this);
        }
    }
}
