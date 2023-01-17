package com.caiotfernandes.assembleiaCoop.resources;

import com.caiotfernandes.assembleiaCoop.ApplicationConfigTest;
import com.caiotfernandes.assembleiaCoop.domain.dtos.PautaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PautaControllerTest extends ApplicationConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void insertPauta_BadRequest() throws Exception {
        URI uri = new URI("/pautas");

        PautaDTO dto = new PautaDTO(null, null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_BAD_REQUEST));
    }

    @Test
    @Order(2)
    void insertPauta_Success() throws Exception {
        URI uri = new URI("/pautas");

        PautaDTO dto = new PautaDTO(null, "PautaTest");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_CREATED));
    }

    @Test
    @Order(3)
    void getPauta_NotFound() throws Exception{
        URI uri = new URI("/pautas/10");

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_NOT_FOUND));
    }

    @Test
    @Order(4)
    void getPauta_Success() throws Exception{
        URI uri = new URI("/pautas/4");

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        PautaDTO PautaDTO = new ObjectMapper().readValue(contentAsString, PautaDTO.class);
        assertEquals(4L, PautaDTO.getId());
        assertEquals("PautaTest", PautaDTO.getName());
    }
}