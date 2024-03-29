package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    public String[] getCodes() {
        String codes = this.getProperties().getProperty("codes");
        String[] eventCodes;

        if (codes != null) {
            return eventCodes = codes.split(",");
        }

        return eventCodes = new String[0];
    }

    /**
     * Add a code to the list of codes from recent events.
     * TODO: IMPLEMENT and make a check if this code actually exist before adding
     *
     * @param code for an event
     */
    public void addCode(String code) throws IOException {
        properties.setProperty("codes", "codes" + "," + code);
        save(properties);
    }

    /**
     * Getter for the "server" property.
     * Gets it from the config file, if it does not exist
     * it sets and saves a default.
     *
     * @return String containing server address
     */
    public String getServer() {
        String configServer = this.getProperties().getProperty("server");

        if (configServer != null) {
            return configServer;
        }

        properties.setProperty("server", "http://localhost:8080/");
        try {
            save(properties);
        } catch (IOException e) {
            System.out.println("The default server could not be saved");
        }
        return "http://localhost:8080/";
    }

    /**
     * Setter for the "server" property.
     * TODO: Make this actually used by server switch
     *
     * @param server address
     */
    public void changeServer(String server) throws IOException {
        properties.setProperty("server", server);
        save(properties);
    }

    /**
     * General save method for property values.
     *
     * @throws FileNotFoundException if config file cant be found
     */
    public void save(Properties properties) throws IOException {

        FileOutputStream output = null;

        // find the config file
        try {
            output = new FileOutputStream("src/main/resources/config.properties");
        } catch (FileNotFoundException e) {
            System.out.println("The config file could not be found");
        }

        // save the config file
        if (output != null) {

            try {
                properties.store(output, null);
            } catch (IOException io) {
                System.out.println("The config file could not be saved");
            }

            output.close();
        }
    }
}
