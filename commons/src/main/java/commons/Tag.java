package commons;

/** This is a Tag class.
 */
public class Tag {
    String name;
    // TODO: decide how to store the Tag colour
    Colour colour;

    /**
     * Create a Tag.
     *
     * @param name Name of the Tag
     * @param colour The Colour needed
     */
    public Tag(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
    }

    // TODO: create Tag with different colour specification methods

    // TODO: create equals and hash function once the storage type of the Tag colour is determined
}
