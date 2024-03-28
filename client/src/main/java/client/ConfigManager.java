package client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * config manager for the client.
 * Loads fields from the config.properties file, which then can
 * be accessed from main statically.
 */
public class ConfigManager {

    private Properties properties;

    /**
     * Constructor for the configManager.
     */
    public ConfigManager() {
        try {
            this.properties = loadProperties();
        } catch (IOException e) {
            //TODO: make sure a config file is created if it does not exist
            System.out.println("There was a problem reading the config file.");
        }
    }

    /**
     * loads the properties from the config file.
     *
     * @return properties
     * @throws IOException if config file is not found
     */
    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream("src/main/resources/config.properties");
        properties.load(input);
        return properties;
    }

    /**
     * Getter for the loaded properties.
     *
     * @return the properties
     */
    private Properties getProperties() {
        return properties;
    }

    /**
     * Getter for the "codes" property.
     *
     * @return String of codes
     */
    public String getCodes() {
        return this.getProperties().getProperty("codes");
    }

    /**
     * Add a code to the list of codes from recent events.
     * TODO: IMPLEMENT and make a check if this code actually exist before adding
     *
     * @param code for an event
     */
    public void addCode(String code) {
        properties.setProperty("codes", "codes" + "," + code);
    }

    /**
     * Getter for the "server" property.
     *
     * @return String containing server address
     */
    public String getServer() {
        return this.getProperties().getProperty("server");
    }

    /**
     * Setter for the "server" property.
     * TODO: Make this actually used by server switch
     *
     * @param server address
     */
    public void changeServer(String server) {
        properties.setProperty("server", server);
    }

}
