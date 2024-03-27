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

    Properties properties;

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
        FileInputStream input = new FileInputStream("client/src/main/resources/config.properties");
        properties.load(input);
        return properties;
    }

    /**
     * Getter for the loaded properties.
     *
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }
}
