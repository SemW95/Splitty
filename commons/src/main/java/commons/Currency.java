package commons;

/**
 * TODO.
 */
public class Currency {
    String name;

    public Currency(String name) {
        this.name = name;
    }

    // TODO: create equals and hash functions once this Object is thought out


    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                '}';
    }
}
