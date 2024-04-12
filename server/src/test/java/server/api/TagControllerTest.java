package server.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Colour;
import commons.Tag;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import server.service.TagService;

@WebMvcTest(TagController.class)
@ActiveProfiles("test")
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(new TagController(tagService)).build();
    }

    @Test
    void getAllTags() throws Exception {
        Tag tag1 = new Tag("Work", new Colour("#FF0000"));
        Tag tag2 = new Tag("Personal", new Colour("#0000FF"));
        when(tagService.getAllTag()).thenReturn(List.of(tag1, tag2));

        mockMvc.perform(get("/tag"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name", is("Work")))
            .andExpect(jsonPath("$[1].name", is("Personal")));
    }

    @Test
    void addTag() throws Exception {
        Tag newTag = new Tag("Health", new Colour("#00FF00"));
        doNothing().when(tagService).addTag(newTag);

        mockMvc.perform(post("/tag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTag)))
            .andExpect(status().isCreated());

        verify(tagService).addTag(newTag);
    }

    @Test
    void getTagById() throws Exception {
        Tag tag = new Tag("Work", new Colour("#FF0000"));
        when(tagService.getTagById("1")).thenReturn(tag);

        mockMvc.perform(get("/tag/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Work")))
            .andExpect(jsonPath("$.colour.red", is(255)));
    }

    @Test
    void getName() throws Exception {
        when(tagService.getName("1")).thenReturn("Work");

        mockMvc.perform(get("/tag/1/name"))
            .andExpect(status().isOk())
            .andExpect(content().string("Work"));
    }

    @Test
    void setName() throws Exception {
        doNothing().when(tagService).setName("1", "New Work");

        mockMvc.perform(put("/tag/1/name/New Work")
                .contentType(MediaType.TEXT_PLAIN))
            .andExpect(status().isOk());

        verify(tagService).setName("1", "New Work");
    }

    @Test
    void getColour() throws Exception {
        when(tagService.getColour("1")).thenReturn(new Colour("#FF0000"));

        mockMvc.perform(get("/tag/1/colour"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.red", is(255)));
    }

    @Test
    void setColour() throws Exception {
        Colour newColour = new Colour("#00FF00");
        doNothing().when(tagService).setColour("1", newColour);

        mockMvc.perform(put("/tag/1/colour")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newColour)))
            .andExpect(status().isOk());

        verify(tagService).setColour("1", newColour);
    }

    @Test
    void setColourWithHexString() throws Exception {
        String hexColor = "00FF00";
        doNothing().when(tagService).setColour("1", hexColor);

        mockMvc.perform(put("/tag/1/colour/hex/" + hexColor)
                .contentType(MediaType.TEXT_PLAIN))
            .andExpect(status().isOk());

        verify(tagService).setColour("1", hexColor);
    }

    @Test
    void setColourWithRedGreenBlue() throws Exception {
        int red = 0;
        int green = 255;
        int blue = 0;
        doNothing().when(tagService).setColour("1", red, green, blue);

        mockMvc.perform(put("/tag/1/colour/rgb/" + red + "/" + green + "/" + blue))
            .andExpect(status().isOk());

        verify(tagService).setColour("1", red, green, blue);
    }

    // Implementations for testSetColour and testSetColour1 go here,
    // assuming they are similar to setColour

    @Test
    void deleteTag() throws Exception {
        doNothing().when(tagService).deleteTag("1");

        mockMvc.perform(delete("/tag/1"))
            .andExpect(status().isOk());

        verify(tagService).deleteTag("1");
    }

    @Test
    void handleIllegalStateException() throws Exception {
        when(tagService.getTagById("unknown")).thenThrow(new IllegalStateException("No tag found"));

        mockMvc.perform(get("/tag/unknown"))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertInstanceOf(IllegalStateException.class,
                result.getResolvedException()))
            .andExpect(
                result -> assertEquals(
                    "No tag found", result.getResolvedException().getMessage()));
    }
}