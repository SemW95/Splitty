package server.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import commons.Colour;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


/**
 * Test fot Colour repository.
 */
@DataJpaTest
public class ColourRepositoryTest {
    @Autowired
    private ColourRepository colourRepository;
    private Colour colourTest;

    @BeforeEach
    void setUp() {
        colourTest = new Colour(50, 100, 150);
        colourRepository.save(colourTest);
    }

    @AfterEach
    void tearDown() {
        colourRepository.delete(colourTest);
    }

    @Test
    void findColourById() {
        Colour colour = colourRepository.findColourById(colourTest.getId()).orElse(null);
        assertNotNull(colour);
        assertEquals(colourTest.getRed(), colour.getRed());
        assertEquals(colourTest.getGreen(), colour.getGreen());
        assertEquals(colourTest.getBlue(), colour.getBlue());
    }

}
