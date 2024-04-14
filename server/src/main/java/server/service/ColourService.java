package server.service;

import commons.Colour;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ColourRepository;

/**
 * Service for Colour. [CONT -> SERV -> REPO]
 */
@Service
public class ColourService {

    private final ColourRepository colourRepository;

    @Autowired
    public ColourService(ColourRepository colourRepository) {
        this.colourRepository = colourRepository;
    }

    public List<Colour> getAllColour() {
        return colourRepository.findAll();
    }

    /**
     * Adds a Colour object to the database.
     *
     * @param colour that is to be added
     * @return The id of the added Colour
     * @throws IllegalStateException When the Colour already exists
     */
    public String createColour(Colour colour) throws IllegalStateException {
        if (colour.getId() == null || !colourRepository.existsById(colour.getId())) {
            return colourRepository.save(colour).getId();
        }

        throw new IllegalStateException(
            "There already is a Colour with this id"
        );
    }

    /**
     * Adds a Colour object to the database.
     *
     * @param hexString a hexadecimal string representing a colour
     * @return The id of the added Colour
     */
    public String createColour(String hexString) {
        return colourRepository.save(new Colour(hexString)).getId();
    }

    /**
     * Adds a Colour object to the database.
     *
     * @param red   The red colour channel
     * @param green   The green colour channel
     * @param blue   The blue colour channel
     * @return The id of the added Colour
     */
    public String createColour(int red, int green, int blue) {
        return colourRepository.save(new Colour(red, green, blue)).getId();
    }

    /** Updates a Colour in the database.
     *
     * @param colour The Colour Object with the updated data
     * @throws IllegalStateException When there isn't a Colour with this id in the database
     */
    public void updateColour(Colour colour) throws IllegalStateException {
        Optional<Colour> optionalColour = colourRepository.findById(colour.getId());

        if (optionalColour.isEmpty()) {
            throw new IllegalStateException("Colour not found");
        }

        colourRepository.save(colour);
    }

    /**
     * Gets a Colour by its id.
     *
     * @param id The id of the Colour
     * @return The requested Colour
     * @throws IllegalStateException When there doesn't exist a Colour with that id
     */
    public Colour getColourById(String id) throws IllegalStateException {
        Optional<Colour> optionalColour = colourRepository.findById(id);

        if (optionalColour.isEmpty()) {
            throw new IllegalStateException("Colour not found");
        }

        return optionalColour.get();
    }

    public int getRed(String id) throws IllegalStateException {
        return getColourById(id).getRed();
    }

    public int getGreen(String id) throws IllegalStateException {
        return getColourById(id).getGreen();
    }

    public int getBlue(String id) throws IllegalStateException {
        return getColourById(id).getBlue();
    }

    /** Sets the red channel of a Colour.
     *
     * @param id The id of the Colour
     * @param red The new red channel value
     * @throws IllegalStateException When there does not exist a Colour with that id
     */
    public void setRed(String id, int red) throws IllegalStateException {
        Colour colour = getColourById(id);

        colour.setRed(red);
        colourRepository.save(colour);
    }

    /** Sets the green channel of a Colour.
     *
     * @param id The id of the Colour
     * @param green The new green channel value
     * @throws IllegalStateException When there does not exist a Colour with that id
     */
    public void setGreen(String id, int green) throws IllegalStateException {
        Colour colour = getColourById(id);

        colour.setGreen(green);
        colourRepository.save(colour);
    }

    /** Sets the blue channel of a Colour.
     *
     * @param id The id of the Colour
     * @param blue The new blue channel value
     * @throws IllegalStateException When there does not exist a Colour with that id
     */
    public void setBlue(String id, int blue) throws IllegalStateException {
        Colour colour = getColourById(id);

        colour.setBlue(blue);
        colourRepository.save(colour);
    }

    public String toHexString(String id) {
        return getColourById(id).toHexString();
    }
}
