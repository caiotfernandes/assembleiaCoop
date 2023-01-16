package com.caiotfernandes.assembleiaCoop.services;

import com.caiotfernandes.assembleiaCoop.domain.dtos.SessaoDTO;
import com.caiotfernandes.assembleiaCoop.domain.dtos.SessaoResultadoDTO;
import com.caiotfernandes.assembleiaCoop.domain.dtos.VotoSessaoDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Associado;
import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import com.caiotfernandes.assembleiaCoop.domain.entities.Sessao;
import com.caiotfernandes.assembleiaCoop.domain.entities.VotoSessao;
import com.caiotfernandes.assembleiaCoop.domain.enums.Voto;
import com.caiotfernandes.assembleiaCoop.repositories.AssociadoRepository;
import com.caiotfernandes.assembleiaCoop.repositories.SessaoRepository;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ClosedSessionException;
import com.caiotfernandes.assembleiaCoop.services.exceptions.InvalidDateException;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoRepository associadoRepository;

    public Sessao getSessionById(Long id) {
        Optional<Sessao> opt = sessaoRepository.findById(id);
        return opt.orElseThrow(() ->
                new ObjectNotFoundException("Sessão de ID: " + id + " não encontrada"));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Sessao openSession(SessaoDTO sessaoDTO) {
        Sessao sessao = fromDTO(sessaoDTO);
        sessao.setStartDate(new Date());

        // Default 1 minute after startDate
        if (sessao.getEndDate() == null) {
            sessao.setEndDate(new Date(sessao.getStartDate().getTime() + 60 * 1000));
        } else if (sessao.getStartDate().after(sessao.getEndDate())) {
            throw new InvalidDateException("A data de expiração da sessão deve ser futura caso informada.");
        }

        Pauta pauta = pautaService.getPauta(sessaoDTO.getPautaId());
        sessao.setPauta(pauta);

        return sessaoRepository.save(sessao);
    }

    public void addVotoSessao(VotoSessaoDTO votoSessaoDTO) {
        Sessao sessao = getSessionById(votoSessaoDTO.getSessaoId());

        if (new Date().after(sessao.getEndDate())) {
            throw new ClosedSessionException("Sessão de pauta: " + sessao.getPauta().getName() + " já encerrada.");
        }

        Optional<Associado> optAssociado = associadoRepository.findById(votoSessaoDTO.getAssociadoId());
        Associado associado = optAssociado.orElseThrow(() ->
                new ObjectNotFoundException("Associado de ID: " + votoSessaoDTO.getAssociadoId() + " não encontrado."));

        if (!votoSessaoDTO.getVoto().equalsIgnoreCase("SIM") &&
                !votoSessaoDTO.getVoto().equalsIgnoreCase("NÃO") &&
                !votoSessaoDTO.getVoto().equalsIgnoreCase("NAO")) {
            throw new IllegalStateException("O Voto deve ser 'SIM' ou 'NÃO'");
        }

        VotoSessao votoSessao = new VotoSessao(sessao, associado, votoSessaoDTO.getVoto().equalsIgnoreCase("SIM") ? Voto.SIM : Voto.NAO);

        for (VotoSessao vt : sessao.getVotoList()) {
            if (vt.equals(votoSessao)) {
                throw new IllegalStateException("Cada associado só pode votar 1 vez por sessão.");
            }
        }

        sessao.getVotoList().add(votoSessao);
        sessaoRepository.save(sessao);
    }

    public SessaoResultadoDTO getResult(Long sessionId) {

        Sessao sessao = getSessionById(sessionId);

        long qtdSim = sessao.getVotoList().stream()
                .map(e -> e.getVoto())
                .filter(e -> e.equals(Voto.SIM))
                .count();

        long qtdNao = sessao.getVotoList().stream()
                .map(e -> e.getVoto())
                .filter(e -> e.equals(Voto.NAO))
                .count();

        SessaoResultadoDTO.SessaoResultadoDTOBuilder dtoBuilder =
                SessaoResultadoDTO.builder()
                .sessaoId(sessionId)
                .nomePauta(sessao.getPauta().getName())
                .quantidadeVotos(sessao.getVotoList().stream().count());

        if (qtdSim > qtdNao) {
            dtoBuilder.resultado(Voto.SIM.toString());
        } else if (qtdNao > qtdSim) {
            dtoBuilder.resultado(Voto.NAO.toString());
        } else {
            dtoBuilder.resultado("Empate");
        }

        return dtoBuilder.build();
    }

    public static Sessao fromDTO(SessaoDTO sessaoDTO) {
        return Sessao.builder()
                .endDate(sessaoDTO.getEndDate())
                .build();
    }

}
