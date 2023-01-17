package com.caiotfernandes.assembleiaCoop.resources;

import com.caiotfernandes.assembleiaCoop.ApplicationConfigTest;
import com.caiotfernandes.assembleiaCoop.domain.dtos.AssociadoDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssociadoControllerTest extends ApplicationConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void insertAssociado_BadRequest() throws Exception {
        URI uri = new URI("/associados");

        AssociadoDTO dto = new AssociadoDTO(null, null);

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
    void insertAssociado_Success() throws Exception {
        URI uri = new URI("/associados");

        AssociadoDTO dto = new AssociadoDTO(null, "AssociadoTest");

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
    void getAssociado_NotFound() throws Exception{
        URI uri = new URI("/associados/10");

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_NOT_FOUND));
    }

    @Test
    @Order(4)
    void getAssociado_Success() throws Exception{
        URI uri = new URI("/associados/4");

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        AssociadoDTO associadoDTO = new ObjectMapper().readValue(contentAsString, AssociadoDTO.class);
        assertEquals(4L, associadoDTO.getId());
        assertEquals("AssociadoTest", associadoDTO.getName());
    }
}