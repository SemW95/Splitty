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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @OneToOne
    private Colour colour;

    /**
     * Empty constructor for JPA.
     */
    public Tag() {
    }

    /** Create a Tag.
     *
     * @param name   Name of the Tag
     * @param colour The Colour needed
     */
    public Tag(
        String name,
        Colour colour
    ) {
        this.name = name;
        this.colour = colour;
    }

    /** Create a Tag with red, green & blue colour values.
     *
     * @param name  Name of the Tag
     * @param red   red index
     * @param green green index
     * @param blue  blue index
     */
    public Tag(
        String name,
        int red,
        int green,
        int blue
    ) {
        this(
            name,
            new Colour(red, green, blue)
        );
    }


    /** Create a Tag a hexString colour value.
     *
     * @param name  Name of the Tag
     * @param hexString hexString of the colour
     */
    public Tag(
        String name,
        String hexString
    ) {
        this(
            name,
            new Colour(hexString)
        );
    }


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

    public String getId() {
        return id;
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
