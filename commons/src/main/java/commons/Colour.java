package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

/**
 * Class Colour.
 */
@Entity
public class Colour {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    int red;
    int green;
    int blue;

    /**
     * A default constructor for JPA.
     */
    public Colour() {
    }

    /**
     * Create a Colour with three integers.
     * All parts have to be in the range [0,255].
     *
     * @param red   red index
     * @param green green index
     * @param blue  blue index
     */
    public Colour(int red, int green, int blue) {
        // Check if all integers are in the range [0,255]
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException("All integers should be in the range [0,255]");
        }

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Create a Colour with the hexString.
     *
     * @param hexString a hexadecimal string representing a colour
     */
    public Colour(String hexString) {
        if (!hexString.startsWith("#")) {
            hexString = "#" + hexString;
        }
        // Check if it matches the hexadecimal color format
        if (!hexString.matches("^#[0-9A-Fa-f]{6}$")) {
            throw new IllegalArgumentException(
                    "The hex string does not match the hexadecimal color format");
        }

        // Change the string to colour index
        String redPart = hexString.substring(1, 3);
        String greenPart = hexString.substring(3, 5);
        String bluePart = hexString.substring(5, 7);

        // Create a Colour
        this.red = Integer.valueOf(redPart, 16);
        this.green = Integer.valueOf(greenPart, 16);
        this.blue = Integer.valueOf(bluePart, 16);
    }

    /**
     * Getter of the red index.
     *
     * @return The red index
     */
    public int getRed() {
        return red;
    }

    /**
     * Setter of the red index.
     *
     * @param red The new red index
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * Getter of the green index.
     *
     * @return The green index
     */
    public int getGreen() {
        return green;
    }

    /**
     * Setter of the green index.
     *
     * @param green The new green index
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * Getter of the green index.
     *
     * @return The green index
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Setter of the blue index.
     *
     * @param blue The new blue index
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * Creates a hexadecimal string from the red, green and blue parts.
     * Note: the letters are lowercase.
     *
     * @return a hexadecimal colour string
     */
    public String toHexString() {
        return String.format("#%02x%02x%02x", red, green, blue);
    }

    /**
     * Check if another Colour is equal to the one.
     *
     * @param o The other Colour
     * @return Ture if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Colour colour = (Colour) o;
        return red == colour.red && green == colour.green && blue == colour.blue;
    }

    /**
     * Calculate the hashcode of the Colour.
     *
     * @return The hashcode of the Colour
     */
    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }

    @Override
    public String toString() {
        return "Colour{" + "red=" + red + ", green=" + green + ", blue=" + blue + '}';
    }

    public String getId() {
        return id;
    }
}
