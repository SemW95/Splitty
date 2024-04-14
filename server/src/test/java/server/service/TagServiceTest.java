package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commons.Colour;
import commons.Tag;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.TagRepository;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTag() {
        // Setup
        Tag tag1 = new Tag("Work", new Colour("#ff0000"));
        Tag tag2 = new Tag("Personal", new Colour("#0000ff"));
        when(tagRepository.findAll()).thenReturn(List.of(tag1, tag2));

        // Execution
        List<Tag> tags = tagService.getAllTag();

        // Assertion
        assertNotNull(tags);
        assertEquals(2, tags.size());
        verify(tagRepository).findAll();
    }

    @Test
    void getTagById() {
        // Setup
        Tag tag = new Tag("Work", new Colour(255, 0, 0));
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));

        // Execution
        Tag result = tagService.getTagById("1");

        // Assertion
        assertNotNull(result);
        assertEquals("Work", result.getName());
        assertEquals("#ff0000", result.getColour().toHexString());
    }

    @Test
    void addTag() {
        // Setup
        Tag newTag = new Tag("Health", new Colour("#00ff00"));
        when(tagRepository.findById("3")).thenReturn(Optional.empty());

        // Execution
        tagService.addTag(newTag);

        // Assertion
        verify(tagRepository).save(newTag);
    }

    @Test
    void getName() {
        // Setup
        Tag tag = new Tag("Work", new Colour("#ff0000"));
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));

        // Execution
        String name = tagService.getName("1");

        // Assertion
        assertEquals("Work", name);
    }

    @Test
    void getColour() {
        // Setup
        Tag tag = new Tag("Work", new Colour(0, 255, 0));
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));

        // Execution
        Colour colour = tagService.getColour("1");

        // Assertion
        assertEquals("#00ff00", colour.toHexString());
    }

    @Test
    void setName() {
        // Setup
        Tag tag = new Tag("Work", new Colour("#ff0000"));
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));

        // Execution
        tagService.setName("1", "New Work");

        // Assertion
        assertEquals("New Work", tag.getName());
        verify(tagRepository).save(tag);
    }

    @Test
    void setColour() {
        // Setup
        Tag tag = new Tag("Work", new Colour("#ff0000"));
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));
        Colour newColour = new Colour("#00ff00");

        // Execution
        tagService.setColour("1", newColour);

        // Assertion
        assertEquals(newColour, tag.getColour());
        verify(tagRepository).save(tag);
    }

    @Test
    void testSetColourWithHexString() {
        // Setup
        Tag tag = new Tag("Work", new Colour("#ff0000"));
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));

        // Execution
        tagService.setColour("1", "00ff00");  // Assuming green HEX

        // Assertion
        assertEquals(new Colour("00ff00"), tag.getColour());
        verify(tagRepository).save(tag);
    }

    @Test
    void testSetColourWithRedGreenBlue() {
        // Setup
        Tag tag = new Tag("Work", new Colour("#ff0000"));
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));

        // Execution
        tagService.setColour("1", 0, 255, 0);  // RGB for green

        // Assertion
        assertEquals(new Colour(0, 255, 0), tag.getColour());
        verify(tagRepository).save(tag);
    }

    @Test
    void deleteTag() {
        // Setup
        Tag tag = new Tag("Work", new Colour("#ff0000"));
        when(tagRepository.findById("1")).thenReturn(Optional.of(tag));

        // Execution
        tagService.deleteTag("1");

        // Assertion
        verify(tagRepository).deleteById("1");
    }
}
