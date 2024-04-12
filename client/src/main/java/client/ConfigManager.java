package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * config manager for the client.
 * Loads fields from the config.properties file, which then can
 * be accessed from main statically.
 */
public class ConfigManager {

    private final Properties properties;

    /**
     * Constructor for the configManager.
     */
    public ConfigManager() {
        this.properties = loadProperties();
    }

    /**
     * loads the properties from the config file.
     *
     * @return properties
     */
    private Properties loadProperties() {
        Properties properties = new Properties();
        try {
            File file = new File("src/main/resources/config.properties");
            file.createNewFile();
            FileInputStream input = new FileInputStream(file);
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    /**
     * Getter for the "codes" property.
     *
     * @return String of codes
     */
    public List<String> getCodes() {
        String codes = properties.getProperty("codes");

        if (codes != null) {
            return new ArrayList<>(Arrays.asList(codes.split(",")));
        }

        properties.setProperty("codes", "");
        save();
        return new ArrayList<>();
    }

    /**
     * Add a code to the list of codes from recent events.
     *
     * @param code for an event
     */
    public void addCode(String code) {
        List<String> codes = getCodes();
        if (codes.contains(code)) {
            return;
        }
        codes.add(code);
        properties.setProperty("codes", String.join(",", codes));
        save();
    }

    /**
     * Remove a code to the list of codes from recent events.
     *
     * @param code for an event
     */
    public void removeCode(String code) {
        List<String> codes = getCodes();
        if (!codes.contains(code)) {
            return;
        }
        codes.remove(code);
        properties.setProperty("codes", String.join(",", codes));
        save();
    }

    /**
     * Getter for the "server" property.
     * Gets it from the config file, if it does not exist
     * it sets and saves a default.
     *
     * @return String containing server address
     */
    public String getServer() {
        String configServer = properties.getProperty("server");

        if (configServer != null) {
            return configServer;
        }

        String initialServer = "localhost:8080";
        properties.setProperty("server", initialServer);
        save();

        return initialServer;
    }

    public String getHttpServer() {
        return "http://" + getServer();
    }

    public String getWsServer() {
        // TODO: could maybe use some url builder
        return "ws://" + getServer() + "/websocket";
    }

    /**
     * Setter for the "server" property.
     * TODO: Make this actually used by server switch
     *
     * @param server address
     */
    public void changeServer(String server) {
        properties.setProperty("server", server);
        save();
    }

    /**
     * Getter for the "language" property.
     */
    public void getLanguage() {
        String languageCode = properties.getProperty("language");

        if (languageCode != null) {

        }

        properties.setProperty("language", "");
        save();
    }

    /**
     * Setter for "language" property.
     */
    public void setLanguage(String languageCode) {

    }

    /**
     * General save method for property values.
     * Tries to save the current state of Properties into the config file
     */
    private void save() {
        FileOutputStream output = null;

        // find the config file
        try {
            output = new FileOutputStream("src/main/resources/config.properties");
        } catch (FileNotFoundException e) {
            System.err.println("The config file could not be found");
        }

        // save the config file
        if (output != null) {
            try {
                properties.store(output, null);
                output.close();
            } catch (IOException io) {
                System.err.println("The config file could not be saved");
            }
        }
    }
}
