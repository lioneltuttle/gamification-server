package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.rest.RecapResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the RecapServiceResource REST controller.
 *
 * @see RecapResource
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class RecapResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        RecapResource recapServiceResource = new RecapResource(null);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(recapServiceResource)
            .build();
    }

    /**
     * Test getFullRecap
     */
    @Test
    public void testGetFullRecap() throws Exception {
        restMockMvc.perform(get("/api/recap-service/get-full-recap"))
            .andExpect(status().isOk());
    }

    /**
     * Test getRecap
     */
    @Test
    public void testGetRecap() throws Exception {
        restMockMvc.perform(get("/api/recap-service/get-recap"))
            .andExpect(status().isOk());
    }
}
