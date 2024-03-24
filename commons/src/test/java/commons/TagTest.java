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
        Tag tagTest2 = new Tag("tagTest1", 50, 100, 150);
        Tag tagTest3 = new Tag("tagTest1", "326496");
        Tag tagTest4 = new Tag("tagTest3", colourTest1);
        Tag tagTest5 = new Tag("tagTest1", colourTest2);
        assertEquals(tagTest1, tagTest2);
        assertEquals(tagTest1, tagTest3);
        assertNotEquals(tagTest1, tagTest4);
        assertNotEquals(tagTest1, tagTest5);
        assertEquals(tagTest1, tagTest1);
        assertNotEquals(tagTest1, colourTest1);
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

    @Test
    void getName() {
        Colour colourTest1 = new Colour(50, 100, 150);
        Tag tagTest1 = new Tag("tagTest1", colourTest1);
        assertEquals("tagTest1", tagTest1.getName());
    }

    @Test
    void setName() {
        Colour colourTest1 = new Colour(50, 100, 150);
        Tag tagTest1 = new Tag("tagTest1", colourTest1);
        tagTest1.setName("newName");
        assertEquals("newName", tagTest1.getName());
    }

    @Test
    void getColour() {
        Colour colourTest1 = new Colour(50, 100, 150);
        Tag tagTest1 = new Tag("tagTest1", colourTest1);
        assertEquals(colourTest1, tagTest1.getColour());
    }

    @Test
    void setColour() {
        Colour colourTest1 = new Colour(50, 100, 150);
        Colour colourTest2 = new Colour(0, 50, 100);
        Tag tagTest1 = new Tag("tagTest1", colourTest1);
        tagTest1.setColour(colourTest2);
        assertEquals(colourTest2, tagTest1.getColour());
    }
}