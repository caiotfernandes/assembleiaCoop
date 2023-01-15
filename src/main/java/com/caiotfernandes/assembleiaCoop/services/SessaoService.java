package com.caiotfernandes.assembleiaCoop.services;

import com.caiotfernandes.assembleiaCoop.domain.dtos.SessaoDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import com.caiotfernandes.assembleiaCoop.domain.entities.Sessao;
import com.caiotfernandes.assembleiaCoop.repositories.SessaoRepository;
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

    public static Sessao fromDTO(SessaoDTO sessaoDTO) {
        return Sessao.builder()
                .endDate(sessaoDTO.getEndDate())
                .build();
    }

}
