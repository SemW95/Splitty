package client.scenes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import client.ConfigManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the configManager.
 */
public class ConfigManagerTest {

    @BeforeEach
    void setup() {
        /* I can't seem to reference this configManager, but I am able to set the values of the
        file back to the original state before every test*/
        ConfigManager configManager
            = new ConfigManager("src/test/testFiles/configTest.properties");
        configManager.removeCode("1234");
        configManager.removeCode("5678");
        configManager.removeCode("9101112");
        configManager.addCode("1234");
        configManager.addCode("5678");
    }

    @Test
    void getCodesTest() {
        ConfigManager configManager1 =
            new ConfigManager("src/test/testFiles/configTest.properties");
        List<String> codes = configManager1.getCodes();
        List<String> checkCodes = new ArrayList<>(List.of("1234", "5678"));

        assertEquals(codes, checkCodes);
    }

    @Test
    void addCodeTest() {
        ConfigManager configManager1
            = new ConfigManager("src/test/testFiles/configTest.properties");
        configManager1.addCode("9101112");
        List<String> codes = configManager1.getCodes();
        List<String> checkCodes = new ArrayList<>(List.of("1234", "5678", "9101112"));
        assertEquals(codes, checkCodes);
    }

    @Test
    void notAddCodeTest() {
        ConfigManager configManager1
            = new ConfigManager("src/test/testFiles/configTest.properties");
        configManager1.addCode("1234");
        List<String> codes = configManager1.getCodes();
        List<String> checkCodes = new ArrayList<>(List.of("1234", "5678"));
        assertEquals(codes, checkCodes);
    }

    @Test
    void removeCodeTest() {
        ConfigManager configManager1
            = new ConfigManager("src/test/testFiles/configTest.properties");
        configManager1.removeCode("1234");
        List<String> codes = configManager1.getCodes();
        List<String> checkCodes = new ArrayList<>(List.of("5678"));
        assertEquals(codes, checkCodes);
    }


    @Test
    void getServerTest() {
        ConfigManager configManager1
            = new ConfigManager("src/test/testFiles/configTest.properties");
        String server = configManager1.getServer();
        String serverCheck = "localhost:8080";
        assertEquals(server, serverCheck);
    }

    @Test
    void getHttpTest() {
        ConfigManager configManager1
            = new ConfigManager("src/test/testFiles/configTest.properties");
        String server = configManager1.getHttpServer();
        String serverCheck = "http://localhost:8080";
        assertEquals(server, serverCheck);
    }

    @Test
    void getWssTest() {
        ConfigManager configManager1
            = new ConfigManager("src/test/testFiles/configTest.properties");
        String server = configManager1.getWsServer();
        String serverCheck = "ws://localhost:8080/websocket";
        assertEquals(server, serverCheck);
    }

    @Test
    void wrongPathTest() {
        assertThrows(Exception.class, () -> {
            ConfigManager configManager1
                = new ConfigManager("wrong/path");
        });
    }
}
