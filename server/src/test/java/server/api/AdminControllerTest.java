package server.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Event;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.service.AdminService;
import server.service.EventService;

/**
 * The tests for the AdminController.
 */
@WebMvcTest(AdminController.class)
@ActiveProfiles("test")
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;
    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * The preparation for all the AdminControllerTest.java
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(
            new AdminController(adminService, eventService)).build();
    }

    @Test
    public void getAllEventsTest() throws Exception {
        List<Event> mockEvents = Arrays.asList(
            new Event("Event Title 1", "Description 1"),
            new Event("Event Title 2", "Description 2")
        );
        when(eventService.getAllEvent()).thenReturn(mockEvents);
        when(adminService.validatePassword(ArgumentMatchers.eq("123456"))).thenReturn(true);

        mockMvc.perform(get("/admin/event").param("password", "123456"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2))) // Correctly uses ResultMatcher
            .andExpect(jsonPath("$[0].title", is("Event Title 1")))
            .andExpect(jsonPath("$[1].title", is("Event Title 2")));

        verify(eventService, times(1)).getAllEvent();
    }
}
