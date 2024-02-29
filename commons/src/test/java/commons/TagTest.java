package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void testEquals() {
        Colour colourTest1 = new Colour(50, 100, 150);
        Colour colourTest2 = new Colour(0, 50, 100);
        Tag tagTest1 = new Tag("tagTest1", colourTest1);
        Tag tagTest2 = new Tag("tagTest1", colourTest1);
        Tag tagTest3 = new Tag("tagTest3", colourTest1);
        Tag tagTest4 = new Tag("tagTest1", colourTest2);
        assertTrue(tagTest1.equals(tagTest1));
        assertTrue(tagTest1.equals(tagTest2));
        assertFalse(tagTest1.equals(tagTest3));
        assertFalse(tagTest1.equals(tagTest4));
    }

    @Test
    void testHashCode() {
        Colour colourTest1 = new Colour(50, 100, 150);
        Colour colourTest2 = new Colour(0, 50, 100);
        Tag tagTest1 = new Tag("tagTest1", colourTest1);
        Tag tagTest2 = new Tag("tagTest1", colourTest1);
        Tag tagTest3 = new Tag("tagTest3", colourTest1);
        Tag tagTest4 = new Tag("tagTest1", colourTest2);
        assertEquals(tagTest1.hashCode(), tagTest2.hashCode());
        assertNotEquals(tagTest1.hashCode(), tagTest3.hashCode());
        assertNotEquals(tagTest1.hashCode(), tagTest4.hashCode());
    }
}