package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ColourTest {

    @Test
    void getRed1() {
        Colour colourTest = new Colour(50, 100, 150);
        assertEquals(50, colourTest.getRed());
    }

    @Test
    void getGreen1() {
        Colour colourTest = new Colour(50, 100, 150);
        assertEquals(100, colourTest.getGreen());
    }

    @Test
    void getBlue1() {
        Colour colourTest = new Colour(50, 100, 150);
        assertEquals(150, colourTest.getBlue());
    }

    @Test
    void setRed() {
        Colour colourTest = new Colour(50, 100, 150);
        colourTest.setRed(0);
        assertEquals(0, colourTest.getRed());
    }

    @Test
    void setGreen() {
        Colour colourTest = new Colour(50, 100, 150);
        colourTest.setGreen(0);
        assertEquals(0, colourTest.getGreen());
    }

    @Test
    void setBlue() {
        Colour colourTest = new Colour(50, 100, 150);
        colourTest.setBlue(0);
        assertEquals(0, colourTest.getBlue());
    }

    @Test
    void testEquals() {
        Colour colourTest1 = new Colour(50, 100, 150);
        Colour colourTest2 = new Colour(50, 100, 150);
        Colour colourTest3 = new Colour(0, 50, 100);
        assertEquals(colourTest1, colourTest2);
        assertNotEquals(colourTest1, colourTest3);
    }

    @Test
    void testHashCode() {
        Colour colourTest1 = new Colour(50, 100, 150);
        Colour colourTest2 = new Colour(50, 100, 150);
        Colour colourTest3 = new Colour(0, 50, 100);
        assertEquals(colourTest1.hashCode(), colourTest2.hashCode());
        assertNotEquals(colourTest1.hashCode(), colourTest3.hashCode());
    }

    @Test
    void hexConstructorTest() {
        Colour colour1 = new Colour(255, 10, 255);
        Colour colour2 = new Colour("#ff0aff");
        Colour colour3 = new Colour("#Ff0AfF");

        assertEquals(colour1, colour2);
        assertEquals(colour2, colour3);

        Colour colour4 = new Colour("#F90AfF");
        assertNotEquals(colour3, colour4);

        Colour colour5 = new Colour("#00FF00");
        Colour colour6 = new Colour(0, 255, 0);
        assertEquals(colour5, colour6);

        Colour colour7 = new Colour("#123456");
        assertNotEquals(colour3, colour7);

        assertThrows(IllegalArgumentException.class, () -> {
            Colour c = new Colour(0, -1, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Colour c = new Colour(256, 0, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Colour c = new Colour("#");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Colour c = new Colour("#00000");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Colour c = new Colour("#kkkkkk");
        });
    }

    @Test
    void toHexStringTest() {
        Colour colour1 = new Colour(0, 0, 0);
        assertEquals("#000000", colour1.toHexString());

        Colour colour2 = new Colour(0, 10, 255);
        assertEquals("#000aff", colour2.toHexString());

        Colour colour3 = new Colour("#012345");
        assertEquals("#012345", colour3.toHexString());

        Colour colour4 = new Colour("#ffffff");
        assertEquals("#ffffff", colour4.toHexString());

        Colour colour5 = new Colour(255, 255, 255);
        assertEquals("#ffffff", colour5.toHexString());

        Colour colour6 = new Colour("#00FF00");
        assertEquals("#00ff00", colour6.toHexString());

        Colour colour7 = new Colour("#123456");
        assertEquals("#123456", colour7.toHexString());
    }

    @Test
    void testToString() {
        Colour colourTest = new Colour(50, 100, 150);
        assertEquals("Colour{red=50, green=100, blue=150}",colourTest.toString());
    }
}