package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Objects;

/**
 * This is a Tag class.
 */
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    // TODO: decide how to store the Tag colour
    // Now, colour is stored in Colour.
    @OneToOne
    Colour colour;

    /**
     * Empty constructor for JPA.
     */
    public Tag() {
    }

    /**
     * Create a Tag.
     *
     * @param name   Name of the Tag
     * @param colour The Colour needed
     */
    public Tag(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
    }

    // TODO: create Tag with different colour specification methods

    // TODO: create equals and hash function once the storage type of the Tag colour is determined


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name) && Objects.equals(colour, tag.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, colour);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
}
