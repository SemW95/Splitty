package server.service;

import commons.Colour;
import commons.Currency;
import java.util.List;
import java.util.Optional;
import server.database.ColourRepository;

/**
 * Service for Currency. [CONT -> SERV -> REPO]
 */
public class ColourService {

    private final ColourRepository colourRepository;

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
    public String createCurrency(Colour colour) throws IllegalStateException {
        if (colour.getId() == null || !colourRepository.existsById(colour.getId())) {
            return colourRepository.save(colour).getId();
        }

        throw new IllegalStateException(
            "There already is a Currency with this id"
        );
    }

    /**
     * Adds a Colour object to the database.
     *
     * @param hexString a hexadecimal string representing a colour
     * @return The id of the added Colour
     */
    public String createCurrency(String hexString) {
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
    public String createCurrency(int red, int green, int blue) {
        return colourRepository.save(new Colour(red, green, blue)).getId();
    }

    /**
     * Gets a Colour by its id.
     *
     * @param id The id of the Colour
     * @return The requested Colour
     * @throws IllegalStateException When there doesn't exist a Colour with that id
     */
    public Colour getColourById(String id) throws IllegalStateException {
        Optional<Colour> optionalCurrency = colourRepository.findById(id);

        if (optionalCurrency.isEmpty()) {
            throw new IllegalStateException("Colour not found");
        }

        return optionalCurrency.get();
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
