package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;


    protected Gender() {}


    private Gender(GenderBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gender)) return false;
        Gender gender = (Gender) o;
        return Objects.equals(id, gender.id) &&
                Objects.equals(name, gender.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Gender{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class GenderBuilder {
        private Long id;
        private String name;

        public GenderBuilder() {
        }

        public GenderBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public GenderBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public GenderBuilder copy(Gender gender) {
            this.id = gender.id;
            this.name = gender.name;
            return this;
        }

        public Gender build() {
            return new Gender(this);
        }
    }
}
