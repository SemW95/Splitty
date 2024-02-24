package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColourTest {

    @Test
    void getRed() {
        Colour colourTest = new Colour(50,100,150);
        assertEquals(50,colourTest.getRed());
    }

    @Test
    void getGreen() {
        Colour colourTest = new Colour(50,100,150);
        assertEquals(100,colourTest.getGreen());
    }

    @Test
    void getBlue() {
        Colour colourTest = new Colour(50,100,150);
        assertEquals(150,colourTest.getBlue());
    }

    @Test
    void setRed() {
        Colour colourTest = new Colour(50,100,150);
        colourTest.setRed(0);
        assertEquals(0,colourTest.getRed());
    }

    @Test
    void setGreen() {
        Colour colourTest = new Colour(50,100,150);
        colourTest.setGreen(0);
        assertEquals(0,colourTest.getGreen());
    }

    @Test
    void setBlue() {
        Colour colourTest = new Colour(50,100,150);
        colourTest.setBlue(0);
        assertEquals(0,colourTest.getBlue());
    }

    @Test
    void testEquals() {
        Colour colourTest1 = new Colour(50,100,150);
        Colour colourTest2 = new Colour(50,100,150);
        Colour colourTest3 = new Colour(0,50,100);
        assertTrue(colourTest1.equals(colourTest2));
        assertFalse(colourTest1.equals(colourTest3));
    }

    @Test
    void testHashCode() {
        Colour colourTest1 = new Colour(50,100,150);
        Colour colourTest2 = new Colour(50,100,150);
        Colour colourTest3 = new Colour(0,50,100);
        assertEquals(colourTest1.hashCode(),colourTest2.hashCode());
        assertNotEquals(colourTest1.hashCode(),colourTest3.hashCode());
    }
}