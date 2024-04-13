package server.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import commons.Colour;
import commons.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Test for TagRepository.
 */
@DataJpaTest
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ColourRepository colourRepository;
    private Tag tagTest1;
    private Colour colourTest1;

    @BeforeEach
    void setUp() {
        colourTest1 = new Colour(50, 100, 150);
        tagTest1 = new Tag("tagTest1", colourTest1);
        colourRepository.save(colourTest1);
        tagRepository.save(tagTest1);
    }

    @AfterEach
    void tearDown() {
        tagRepository.delete(tagTest1);
        colourRepository.delete(colourTest1);
    }


    @Test
    void findById() {
        Tag tag = tagRepository.findById(tagTest1.getId()).orElse(null);
        assertNotNull(tag);
        assertEquals(tagTest1.getColour(), tag.getColour());
        assertEquals(tagTest1.getName(), tag.getName());
    }

    @Test
    void findTagByName() {
        Tag tag = tagRepository.findFirstByName(tagTest1.getName()).orElse(null);
        assertNotNull(tag);
        assertEquals(tagTest1.getId(), tag.getId());
        assertEquals(tagTest1.getColour(), tag.getColour());
    }


}
