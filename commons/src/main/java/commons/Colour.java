package commons;

public class Colour {
    int id;
    int red;
    int green;
    int blue;
    public Colour(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
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


    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

}
