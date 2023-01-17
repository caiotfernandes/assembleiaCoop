package com.caiotfernandes.assembleiaCoop.services;

import com.caiotfernandes.assembleiaCoop.ApplicationConfigTest;
import com.caiotfernandes.assembleiaCoop.domain.dtos.AssociadoDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Associado;
import com.caiotfernandes.assembleiaCoop.repositories.AssociadoRepository;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AssociadoServiceTest extends ApplicationConfigTest {

    @Autowired
    AssociadoService associadoService;

    @MockBean
    AssociadoRepository associadoRepository;

    @Test
    void findById_assertNotNull() {
        Associado associadoMock = mock(Associado.class);
        Optional<Associado> opt = Optional.of(associadoMock);
        when(associadoRepository.findById(anyLong())).thenReturn(opt);

        assertNotNull(associadoService.findById(1L));
    }

    @Test
    void findById_assertObjectNotFoundException() {
        Optional<Associado> opt = Optional.empty();
        when(associadoRepository.findById(anyLong())).thenReturn(opt);

        Exception exception = assertThrows(ObjectNotFoundException.class, () -> associadoService.findById(1L));

        assertEquals("Associado de ID: 1 não encontrado", exception.getMessage());
    }

    @Test
    void insertAssociado() {
        Associado associadoMock = mock(Associado.class);
        when(associadoRepository.save(any(Associado.class))).thenReturn(associadoMock);
        Associado associado = associadoService.insertAssociado(associadoMock);

        verify(associadoRepository, times(1)).save(any(Associado.class));
        assertNotNull(associado);
    }

    @Test
    void fromDTO_AssertNotNull() {
        AssociadoDTO dtoMock = mock(AssociadoDTO.class);
        assertNotNull(AssociadoService.fromDTO(dtoMock));
    }

    @Test
    void fromDTO_AssertInstanceOfAssociado() {
        AssociadoDTO dtoMock = mock(AssociadoDTO.class);
        assertInstanceOf(Associado.class, AssociadoService.fromDTO(dtoMock));
    }
}