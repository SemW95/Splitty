package commons;

import java.util.Objects;

public class Colour {
    int id;
    int red;
    int green;
    int blue;


    /**
     * Create a Colour with three integers.
     *
     * @param red    red index
     * @param green  green index
     * @param blue   blue index
     */
    public Colour(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Create a Colour with the hexString
     *
     * @param hexString a String represent colour
     */
    public Colour(String hexString){
        if (hexString.matches("^#[0-9A-Fa-f]{6}$")) { // Check if it matches hexadecimal color format
            // Change the string to color
            String redPart = hexString.substring(0,1);
            String greenPart = hexString.substring(2,3);
            String bluePart = hexString.substring(4,5);

            this.red = Integer.valueOf(redPart,16);
            this.green = Integer.valueOf(greenPart,16);
            this.blue = Integer.valueOf(bluePart,16);

        } else {
            System.out.println("Invalid hexadecimal color string.");
            // Default color
            this.red = 0;
            this.green = 0;
            this.blue = 0;
        }
    }

    /**
     * Getter of the red index
     *
     * @return  The red index
     */
    public int getRed() {
        return red;
    }
    /**
     * Getter of the green index
     *
     * @return  The green index
     */
    public int getGreen() {
        return green;
    }
    /**
     * Getter of the green index
     *
     * @return  The green index
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Setter of the red index
     *
     * @param red  The new red index
     */
    public void setRed(int red) {
        this.red = red;
    }
    /**
     * Setter of the green index
     *
     * @param green  The new green index
     */
    public void setGreen(int green) {
        this.green = green;
    }
    /**
     * Setter of the blue index
     *
     * @param blue  The new blue index
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * Check if another Colour is equal to the one
     *
     * @param o  The other Colour
     * @return   Ture if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colour colour = (Colour) o;
        return red == colour.red && green == colour.green && blue == colour.blue;
    }

    /**
     * Calculate the hashcode of the Colour
     *
     * @return The hashcode of the Colour
     */
    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }

}
