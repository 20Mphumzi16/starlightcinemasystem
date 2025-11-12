package za.ac.cput.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String contactNumber;
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    List<Hall> halls;


    public Branch() {
    }

    public Branch(BranchBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.contactNumber = builder.contactNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(id, branch.id) && Objects.equals(name, branch.name) && Objects.equals(address, branch.address) && Objects.equals(contactNumber, branch.contactNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, contactNumber);
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }

    public static class BranchBuilder {
        private Long id;
        private String name;
        private Address address;
        private String contactNumber;

        public BranchBuilder() {
        }

        public BranchBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BranchBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public BranchBuilder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public BranchBuilder setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
            return this;
        }

        public BranchBuilder copy(Branch branch) {
            this.id = branch.id;
            this.name = branch.name;
            this.address = branch.address;
            this.contactNumber = branch.contactNumber;
            return this;
        }

        public Branch build() {
            return new Branch(this);
        }
    }

}
