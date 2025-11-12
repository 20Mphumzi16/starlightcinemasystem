package za.ac.cput.domain;

/**
 * Marker interface for all Value Objects.
 */
public interface ValueObject<T> {
    boolean sameValueAs(T other);
}