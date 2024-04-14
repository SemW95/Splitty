package server.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commons.Colour;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.ColourRepository;

class ColourServiceTest {

    @Mock
    private ColourRepository colourRepository;

    @InjectMocks
    private ColourService colourService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllColour() {
        List<Colour> expectedColours = new ArrayList<>();
        when(colourRepository.findAll()).thenReturn(expectedColours);
        List<Colour> colours = colourService.getAllColour();
        assertEquals(expectedColours, colours);
        verify(colourRepository).findAll();
    }

    @Test
    void createColour_ColourObject() {
        Colour colour = new Colour(255, 0, 0);
        when(colourRepository.save(any(Colour.class))).thenReturn(colour);
        String id = colourService.createColour(colour);
        assertEquals(colour.getId(), id);
        verify(colourRepository).save(colour);
    }

    @Test
    void createColour_HexString() {
        // Create a Colour object with specific attributes
        Colour colour = new Colour("#ff0000");
        colour.setId("123"); // Simulate the ID setting as it would be by the repository on save

        // When the repository saves any Colour, return this specific Colour with an ID
        when(colourRepository.save(any(Colour.class))).thenReturn(colour);

        // Call the service method
        String id = colourService.createColour("#ff0000");

        // Assert non-null ID and verify correct interaction with the repository
        assertNotNull(id);
        assertEquals("123", id); // Confirm the ID is as expected
        verify(colourRepository).save(any(Colour.class));
    }

    @Test
    void createColour_RedGreenBlueValues() {
        // Create a Colour object with specific RGB values
        Colour colour = new Colour(255, 0, 0);
        colour.setId("123"); // Simulate the ID setting as it would be by the repository on save

        // Mock the save method to return the modified Colour object
        when(colourRepository.save(any(Colour.class))).thenReturn(colour);

        // Call the service method
        String id = colourService.createColour(255, 0, 0);

        // Assert non-null ID and verify correct interaction with the repository
        assertNotNull(id);
        assertEquals("123", id); // Confirm the ID is as expected
        verify(colourRepository).save(any(Colour.class));
    }

    @Test
    void updateColour() {
        Colour colour = new Colour(255, 0, 0);
        colour.setId("1");
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        when(colourRepository.save(colour)).thenReturn(colour);
        assertDoesNotThrow(() -> colourService.updateColour(colour));
        verify(colourRepository).save(colour);
    }

    @Test
    void updateColour_NotFound() {
        Colour colour = new Colour(255, 0, 0);
        colour.setId("1");
        when(colourRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> colourService.updateColour(colour));
    }

    @Test
    void getColourById() {
        Colour colour = new Colour(255, 0, 0);
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        Colour foundColour = colourService.getColourById("1");
        assertEquals(colour, foundColour);
        verify(colourRepository).findById("1");
    }

    @Test
    void getRed() {
        Colour colour = new Colour(255, 0, 0);
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        assertEquals(255, colourService.getRed("1"));
    }

    @Test
    void getGreen() {
        Colour colour = new Colour(0, 255, 0);
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        assertEquals(255, colourService.getGreen("1"));
    }

    @Test
    void getBlue() {
        Colour colour = new Colour(0, 0, 255);
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        assertEquals(255, colourService.getBlue("1"));
    }

    @Test
    void setRed() {
        Colour colour = new Colour(0, 0, 0);
        colour.setId("1");
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        when(colourRepository.save(colour)).thenReturn(colour);
        assertDoesNotThrow(() -> colourService.setRed("1", 255));
        assertEquals(255, colour.getRed());
        verify(colourRepository).save(colour);
    }

    @Test
    void setGreen() {
        Colour colour = new Colour(0, 0, 0);
        colour.setId("1");
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        when(colourRepository.save(colour)).thenReturn(colour);
        assertDoesNotThrow(() -> colourService.setGreen("1", 255));
        assertEquals(255, colour.getGreen());
        verify(colourRepository).save(colour);
    }

    @Test
    void setBlue() {
        Colour colour = new Colour(0, 0, 0);
        colour.setId("1");
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        when(colourRepository.save(colour)).thenReturn(colour);
        assertDoesNotThrow(() -> colourService.setBlue("1", 255));
        assertEquals(255, colour.getBlue());
        verify(colourRepository).save(colour);
    }

    @Test
    void toHexString() {
        Colour colour = new Colour(255, 0, 0);
        when(colourRepository.findById("1")).thenReturn(Optional.of(colour));
        assertEquals("#ff0000", colourService.toHexString("1"));
    }
}
