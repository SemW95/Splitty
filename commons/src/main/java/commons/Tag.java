package commons;

/** This is a Tag class.
 */
public class Tag {
    String name;
    // TODO: decide how to store the Tag colour
    String colourCode;

    /**
     * Create a Tag.
     *
     * @param name       Name of the Tag
     * @param colourCode The Colour needed
     */
    public Tag(String name, Colour colourCode) {
        this.name = name;
        this.colourCode = colourCode;
    }

    // TODO: create Tag with different colour specification methods

    // TODO: create equals and hash function once the storage type of the Tag colour is determined
}
