package server.api;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Colour;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.service.ColourService;

@WebMvcTest(ColourController.class)
@ActiveProfiles("test")
class ColourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColourService colourService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ColourController(colourService)).build();
    }

    @Test
    void getAllColour() throws Exception {
        List<Colour> expectedColours = new ArrayList<>();
        expectedColours.add(new Colour("#FF0000"));
        when(colourService.getAllColour()).thenReturn(expectedColours);

        mockMvc.perform(get("/colour"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedColours)));
    }

    @Test
    void getColourById() throws Exception {
        Colour expectedColour = new Colour("#FF0000");
        when(colourService.getColourById("1")).thenReturn(expectedColour);

        mockMvc.perform(get("/colour/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedColour)));
    }

    @Test
    void createColour() throws Exception {
        Colour expectedColour = new Colour("#FF0000");
        expectedColour.setId("1");
        when(colourService.createColour(expectedColour)).thenReturn(expectedColour.getId());

        mockMvc.perform(post("/colour")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expectedColour)))
            .andExpect(status().isCreated());
    }

    @Test
    void createColourRedGreenBlue() throws Exception {
        int red = 50;
        int green = 100;
        int blue = 150;
        doReturn("1").when(colourService).createColour(red, green, blue);

        mockMvc.perform(post("/colour/rgb/" + red + "/" + green + "/" + blue))
            .andExpect(status().isCreated());
    }

    @Test
    void createColourHex() throws Exception {
        String hex = "FF0000";
        when(colourService.createColour(hex)).thenReturn("1");

        mockMvc.perform(post("/colour/hex/" + hex)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().json("1"));
    }

    @Test
    void updateColour() throws Exception {
        Colour colour = new Colour("#FF0000");
        colour.setId("1");

        doNothing().when(colourService).updateColour(colour);
        when(colourService.getColourById("1")).thenReturn(colour);

        mockMvc.perform(put("/colour")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(colour)))
            .andExpect(status().isOk());
    }


    @Test
    void getRed() throws Exception {
        String colourId = "1";
        int expectedRed = 255;
        when(colourService.getRed(colourId)).thenReturn(expectedRed);

        mockMvc.perform(get("/colour/{id}/red", colourId))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(expectedRed)));
    }


    @Test
    void getGreen() throws Exception {
        String colourId = "1";
        int expectedGreen = 120;
        when(colourService.getGreen(colourId)).thenReturn(expectedGreen);

        mockMvc.perform(get("/colour/{id}/green", colourId))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(expectedGreen)));
    }

    @Test
    void getBlue() throws Exception {
        String colourId = "1";
        int expectedBlue = 60;
        when(colourService.getBlue(colourId)).thenReturn(expectedBlue);

        mockMvc.perform(get("/colour/{id}/blue", colourId))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(expectedBlue)));
    }

    @Test
    void setRed() throws Exception {
        String colourId = "1";
        int newRed = 250;
        doNothing().when(colourService).setRed(colourId, newRed);

        mockMvc.perform(put("/colour/{id}/red/{value}", colourId, newRed))
            .andExpect(status().isOk());
    }

    @Test
    void setGreen() throws Exception {
        String colourId = "1";
        int newGreen = 100;
        doNothing().when(colourService).setGreen(colourId, newGreen);

        mockMvc.perform(put("/colour/{id}/green/{value}", colourId, newGreen))
            .andExpect(status().isOk());
    }

    @Test
    void setBlue() throws Exception {
        String colourId = "1";
        int newBlue = 50;
        doNothing().when(colourService).setBlue(colourId, newBlue);

        mockMvc.perform(put("/colour/{id}/blue/{value}", colourId, newBlue))
            .andExpect(status().isOk());
    }

    @Test
    void toHexString() throws Exception {
        String colourId = "1";
        String hexString = "#FF0000";
        when(colourService.toHexString(colourId)).thenReturn(hexString);

        mockMvc.perform(get("/colour/{id}/hex", colourId))
            .andExpect(status().isOk())
            .andExpect(content().string(hexString));
    }


    @Test
    public void handleIllegalStateExceptionTest() throws Exception {
        String invalidExpenseId = "nonExistingId";
        given(colourService.getColourById(invalidExpenseId)).willThrow(
            new IllegalStateException("There is no Expense with this id")
        );

        mockMvc.perform(get("/event/id/{id}", invalidExpenseId))
            .andExpect(status().isNotFound());
    }
}