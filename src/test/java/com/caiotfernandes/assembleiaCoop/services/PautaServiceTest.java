package com.caiotfernandes.assembleiaCoop.services;

import com.caiotfernandes.assembleiaCoop.ApplicationConfigTest;
import com.caiotfernandes.assembleiaCoop.domain.dtos.PautaDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import com.caiotfernandes.assembleiaCoop.repositories.PautaRepository;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PautaServiceTest extends ApplicationConfigTest {

    @Autowired
    PautaService pautaService;

    @MockBean
    PautaRepository pautaRepository;

    @Test
    void getPauta_assertNotNull() {
        Pauta pautaMock = mock(Pauta.class);
        Optional<Pauta> opt = Optional.of(pautaMock);
        when(pautaRepository.findById(anyLong())).thenReturn(opt);

        assertNotNull(pautaService.getPauta(1L));
    }

    @Test
    void getPauta_assertObjectNotFoundException() {
        Optional<Pauta> opt = Optional.empty();
        when(pautaRepository.findById(anyLong())).thenReturn(opt);
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> pautaService.getPauta(1L));

        assertEquals("Pauta de ID: 1 não encontrada", exception.getMessage());
    }

    @Test
    void insertPauta() {
        Pauta pautaMock = mock(Pauta.class);
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaMock);
        Pauta pauta = pautaService.insertPauta(pautaMock);

        verify(pautaRepository, times(1)).save(any(Pauta.class));
        assertNotNull(pauta);
    }

    @Test
    void fromDTO_assertNotNull() {
        PautaDTO dtoMock = mock(PautaDTO.class);
        assertNotNull(PautaService.fromDTO(dtoMock));
    }

    @Test
    void fromDTO_assertInstanceOfPauta() {
        PautaDTO dtoMock = mock(PautaDTO.class);
        assertInstanceOf(Pauta.class, PautaService.fromDTO(dtoMock));
    }

}