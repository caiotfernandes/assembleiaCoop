package com.caiotfernandes.assembleiaCoop.services;

import com.caiotfernandes.assembleiaCoop.ApplicationConfigTest;
import com.caiotfernandes.assembleiaCoop.domain.dtos.SessaoDTO;
import com.caiotfernandes.assembleiaCoop.domain.dtos.VotoSessaoDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Associado;
import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import com.caiotfernandes.assembleiaCoop.domain.entities.Sessao;
import com.caiotfernandes.assembleiaCoop.domain.entities.VotoSessao;
import com.caiotfernandes.assembleiaCoop.domain.enums.Voto;
import com.caiotfernandes.assembleiaCoop.repositories.SessaoRepository;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ClosedSessionException;
import com.caiotfernandes.assembleiaCoop.services.exceptions.InvalidDateException;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("unit-test")
class SessaoServiceTest extends ApplicationConfigTest {

    @Autowired
    SessaoService sessaoService;

    @MockBean
    SessaoRepository sessaoRepository;

    @MockBean
    AssociadoService associadoService;

    @MockBean
    PautaService pautaService;

    @Test
    void getSessionById_AssertNotNull() {
        Sessao sessaoMock = mock(Sessao.class);
        Optional<Sessao> opt = Optional.of(sessaoMock);
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        assertNotNull(sessaoService.getSessionById(1L));
    }

    @Test
    void getSessionById_AssertObjectNotFoundException() {
        Optional<Sessao> opt = Optional.empty();
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        Exception exception = assertThrows(ObjectNotFoundException.class, () -> sessaoService.getSessionById(1L));

        assertEquals("Sessão de ID: 1 não encontrada", exception.getMessage());
    }

    @Test
    void openSession_AssertInvalidDateException() {
        SessaoDTO dto = mock(SessaoDTO.class);
        when(dto.getPautaId()).thenReturn(1L);
        when(dto.getEndDate()).thenReturn(new Date(new Date().getTime() - 1000 * 60));

        Exception e = assertThrows(InvalidDateException.class, () -> sessaoService.openSession(dto));

        assertEquals("A data de expiração da sessão deve ser futura caso informada.", e.getMessage());
    }

    @Test
    void openSession_Success() {
        SessaoDTO dto = mock(SessaoDTO.class);
        when(dto.getPautaId()).thenReturn(1L);
        when(dto.getEndDate()).thenReturn(null);

        when(pautaService.getPauta(anyLong())).thenReturn(mock(Pauta.class));
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(mock(Sessao.class));

        sessaoService.openSession(dto);
        verify(sessaoRepository, times(1)).save(any(Sessao.class));
    }

    @Test
    void addVotoSessao_AssertClosedSessionException() {
        VotoSessaoDTO dto = mock(VotoSessaoDTO.class);
        when(dto.getSessaoId()).thenReturn(1L);
        when(dto.getAssociadoId()).thenReturn(1L);

        Sessao sessaoMock = mock(Sessao.class);
        Optional<Sessao> opt = Optional.of(sessaoMock);
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        when(sessaoMock.getEndDate()).thenReturn(new Date(new Date().getTime() - 1000 * 60));

        Pauta pautaMock = mock(Pauta.class);
        when(sessaoMock.getPauta()).thenReturn(pautaMock);
        when(pautaMock.getName()).thenReturn("PautaTest");

        Exception e = assertThrows(ClosedSessionException.class, () -> sessaoService.addVotoSessao(dto));
        assertEquals("Sessão de pauta: PautaTest já encerrada.", e.getMessage());
    }

    @Test
    void addVotoSessao_AssertIllegalStateException_Voto() {
        VotoSessaoDTO dto = mock(VotoSessaoDTO.class);
        when(dto.getSessaoId()).thenReturn(1L);
        when(dto.getAssociadoId()).thenReturn(1L);
        when(dto.getVoto()).thenReturn("aaa");

        Sessao sessaoMock = mock(Sessao.class);
        Optional<Sessao> opt = Optional.of(sessaoMock);
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        when(sessaoMock.getEndDate()).thenReturn(new Date(new Date().getTime() + 1000 * 60));

        Associado associadoMock = mock(Associado.class);
        when(associadoService.findById(anyLong())).thenReturn(associadoMock);

        Exception e = assertThrows(IllegalStateException.class, () -> sessaoService.addVotoSessao(dto));
        assertEquals("O Voto deve ser 'SIM' ou 'NÃO'", e.getMessage());
    }

    @Test
    void addVotoSessao_AssertIllegalStateException_VotoJaRegistrado() {
        VotoSessaoDTO dto = mock(VotoSessaoDTO.class);
        when(dto.getSessaoId()).thenReturn(1L);
        when(dto.getAssociadoId()).thenReturn(1L);
        when(dto.getVoto()).thenReturn("sim");

        Sessao sessaoMock = mock(Sessao.class);
        Optional<Sessao> opt = Optional.of(sessaoMock);
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        Associado associadoMock = mock(Associado.class);
        when(associadoService.findById(anyLong())).thenReturn(associadoMock);

        when(sessaoMock.getEndDate()).thenReturn(new Date(new Date().getTime() + 1000 * 60));

        when(sessaoMock.getId()).thenReturn(1L);
        when(associadoMock.getId()).thenReturn(1L);

        List<VotoSessao> list = createVotoSessaoSingletonList(sessaoMock, associadoMock, Voto.SIM);
        when(sessaoMock.getVotoList()).thenReturn(list);

        Exception e = assertThrows(IllegalStateException.class, () -> sessaoService.addVotoSessao(dto));
        assertEquals("Cada associado só pode votar 1 vez por sessão.", e.getMessage());
    }

    @Test
    void addVotoSessao_Success() {
        VotoSessaoDTO dto = mock(VotoSessaoDTO.class);
        when(dto.getSessaoId()).thenReturn(1L);
        when(dto.getAssociadoId()).thenReturn(1L);
        when(dto.getVoto()).thenReturn("sim");

        Sessao sessaoMock = mock(Sessao.class);
        Optional<Sessao> opt = Optional.of(sessaoMock);
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        Associado associadoMock = mock(Associado.class);
        when(associadoService.findById(anyLong())).thenReturn(associadoMock);

        when(sessaoMock.getEndDate()).thenReturn(new Date(new Date().getTime() + 1000 * 60));

        when(sessaoMock.getId()).thenReturn(1L);
        when(associadoMock.getId()).thenReturn(1L);

        when(sessaoMock.getVotoList()).thenReturn(new ArrayList<VotoSessao>());

        sessaoService.addVotoSessao(dto);
        verify(sessaoRepository, times(1)).save(any(Sessao.class));

    }

    @Test
    void getResult_Sim() {
        Sessao sessaoMock = mock(Sessao.class);
        Optional<Sessao> opt = Optional.of(sessaoMock);
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        List<VotoSessao> list = createVotoSessaoSingletonList(sessaoMock, mock(Associado.class), Voto.SIM);
        when(sessaoMock.getVotoList()).thenReturn(list);

        when(sessaoMock.getPauta()).thenReturn(mock(Pauta.class));
        assertEquals("SIM", sessaoService.getResult(1L).getResultado());
        assertEquals(1, sessaoService.getResult(1L).getQuantidadeVotos());
    }

    @Test
    void getResult_Nao() {
        Sessao sessaoMock = mock(Sessao.class);
        Optional<Sessao> opt = Optional.of(sessaoMock);
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        List<VotoSessao> list = createVotoSessaoSingletonList(sessaoMock, mock(Associado.class), Voto.NAO);
        when(sessaoMock.getVotoList()).thenReturn(list);

        when(sessaoMock.getPauta()).thenReturn(mock(Pauta.class));
        assertEquals("NAO", sessaoService.getResult(1L).getResultado());
        assertEquals(1, sessaoService.getResult(1L).getQuantidadeVotos());
    }

    @Test
    void getResult_Empate() {
        Sessao sessaoMock = mock(Sessao.class);
        Optional<Sessao> opt = Optional.of(sessaoMock);
        when(sessaoRepository.findById(anyLong())).thenReturn(opt);

        List<VotoSessao> list = createVotoSessaoSingletonList(sessaoMock, mock(Associado.class), Voto.SIM);
        list.addAll(createVotoSessaoSingletonList(sessaoMock, mock(Associado.class), Voto.NAO));
        when(sessaoMock.getVotoList()).thenReturn(list);

        when(sessaoMock.getPauta()).thenReturn(mock(Pauta.class));
        assertEquals("Empate", sessaoService.getResult(1L).getResultado());
        assertEquals(2, sessaoService.getResult(1L).getQuantidadeVotos());
    }

    @Test
    void fromDTO_AssertNotNull() {
        SessaoDTO sessaoDTO = mock(SessaoDTO.class);
        assertNotNull(SessaoService.fromDTO(sessaoDTO));
    }

    @Test
    void fromDTO_AssertInstanceOfSessao() {
        SessaoDTO sessaoDTO = mock(SessaoDTO.class);
        assertInstanceOf(Sessao.class, SessaoService.fromDTO(sessaoDTO));
    }

    private static List<VotoSessao> createVotoSessaoSingletonList(Sessao sessao, Associado associado, Voto voto) {
        List<VotoSessao> votoSessaoList = new ArrayList<>();
        VotoSessao votoSessao = new VotoSessao(sessao, associado, voto);
        votoSessaoList.add(votoSessao);
        return votoSessaoList;
    }

}