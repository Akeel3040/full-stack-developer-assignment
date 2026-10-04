package com.internal.tasktracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void appliesArchivedStatusAndSearchPredicatesTogether() throws Exception {
        mockMvc.perform(get("/api/tasks")
                        .param("q", "api")
                        .param("status", "OPEN")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[?(@.status != 'OPEN')]").doesNotExist())
                .andExpect(jsonPath("$.items[?(@.title == 'Update API rate limiting')]").doesNotExist())
                .andExpect(jsonPath("$.items[?(@.title == 'Legacy API cleanup')]").doesNotExist());
    }

    @Test
    void rejectsInvalidPaginationAndStatusValues() throws Exception {
        mockMvc.perform(get("/api/tasks").param("page", "0"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/tasks").param("pageSize", "101"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/tasks").param("status", "INVALID"))
                .andExpect(status().isBadRequest());
    }
}
