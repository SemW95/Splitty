package commons;

import java.util.Objects;

public class Tag {
    String name;
    Colour colourCode;

    /**
     * Create a Tag
     *
     * @param name         Name of the Tag
     * @param colourCode   The Colour needed
     */
    public Tag(String name, Colour colourCode) {
        this.name = name;
        this.colourCode = colourCode;
    }

    /**
     * Check if the other Object is equals to a Tag
     *
     * @param o The other Object
     * @return  Ture if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name) && Objects.equals(colourCode, tag.colourCode);
    }
    /**
     * Calculate the hashcode of the Tag
     *
     * @return The hashcode of the Tag
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, colourCode);
    }
}
