package com.caiotfernandes.assembleiaCoop.resources;

import com.caiotfernandes.assembleiaCoop.ApplicationConfigTest;
import com.caiotfernandes.assembleiaCoop.domain.dtos.SessaoDTO;
import com.caiotfernandes.assembleiaCoop.domain.dtos.SessaoResultadoDTO;
import com.caiotfernandes.assembleiaCoop.domain.dtos.VotoSessaoDTO;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("integration-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SessaoControllerTest extends ApplicationConfigTest {

    @Autowired
    private MockMvc mockMvc;

    private final Date endDate = new Date(new Date().getTime() + 1000 * 60);

    @Test
    @Order(1)
    void openSession_BadRequest_PautaId() throws Exception {
        URI uri = new URI("/sessao");

        SessaoDTO dto = new SessaoDTO(null, endDate);

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
    void openSession_BadRequest_EndDate() throws Exception {
        URI uri = new URI("/sessao");

        SessaoDTO dto = new SessaoDTO(1l, new SimpleDateFormat("dd-mm-yy").parse("17-01-2023"));

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
    @Order(3)
    void openSession_Success() throws Exception {
        URI uri = new URI("/sessao");

        SessaoDTO dto = new SessaoDTO(1l, endDate);

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
    @Order(4)
    void getSession_NotFound() throws Exception{
        URI uri = new URI("/sessao/10");

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_NOT_FOUND));
    }

    @Test
    @Order(5)
    void getSession_Success() throws Exception{
        URI uri = new URI("/sessao/1");

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        SessaoDTO sessaoDTO = new ObjectMapper().readValue(contentAsString, SessaoDTO.class);
        assertEquals(1L, sessaoDTO.getPautaId());
    }

    @Test
    @Order(6)
    void votarSessao_BadRequest_SessaoId() throws Exception {
        URI uri = new URI("/sessao/votar");

        VotoSessaoDTO dto = new VotoSessaoDTO(null,1l,"sim");

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
    @Order(7)
    void votarSessao_BadRequest_AssociadoId() throws Exception {
        URI uri = new URI("/sessao/votar");

        VotoSessaoDTO dto = new VotoSessaoDTO(1l,null,"sim");

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
    @Order(8)
    void votarSessao_BadRequest_Voto() throws Exception {
        URI uri = new URI("/sessao/votar");

        VotoSessaoDTO dto = new VotoSessaoDTO(1l,1l,null);

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
    @Order(9)
    void votarSessao_Success() throws Exception {
        URI uri = new URI("/sessao/votar");

        VotoSessaoDTO dto = new VotoSessaoDTO(1l,1l,"sim");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));
    }

    @Test
    @Order(10)
    void getResults_NotFound() throws Exception {
        URI uri = new URI("/sessao/resultados/12");

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_NOT_FOUND));
    }

    @Test
    @Order(11)
    void getResults_Success() throws Exception {
        URI uri = new URI("/sessao/resultados/1");

        final ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        SessaoResultadoDTO dto = new ObjectMapper().readValue(contentAsString, SessaoResultadoDTO.class);
        assertEquals(1L, dto.getSessaoId());
        assertEquals("SIM", dto.getResultado());
        assertEquals(1L, dto.getQuantidadeVotos());
    }
}