package za.ac.cput.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats;

    public Hall() {
    }

    public Hall(HallBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.capacity = builder.capacity;
        this.branch = builder.branch;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Branch getBranch() {
        return branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return capacity == hall.capacity && Objects.equals(id, hall.id) && Objects.equals(name, hall.name) && Objects.equals(branch, hall.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, branch);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", branch=" + branch +
                '}';
    }

    public static class HallBuilder {
        private Long id;
        private String name;
        private int capacity;
        private Branch branch;

        public HallBuilder() {

        }

        public HallBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public HallBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public HallBuilder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public HallBuilder setBranch(Branch branch) {
            this.branch = branch;
            return this;
        }

        public HallBuilder copy(Hall hall) {
            this.id = hall.id;
            this.name = hall.name;
            this.capacity = hall.capacity;
            this.branch = hall.branch;
            return this;

        }

        public Hall build() {
            return new Hall(this);
        }
    }
}
