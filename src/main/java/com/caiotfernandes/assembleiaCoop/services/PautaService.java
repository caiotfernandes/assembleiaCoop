package com.caiotfernandes.assembleiaCoop.services;

import com.caiotfernandes.assembleiaCoop.domain.dtos.PautaDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import com.caiotfernandes.assembleiaCoop.repositories.PautaRepository;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PautaService {
    
    @Autowired
    private PautaRepository pautaRepository;

    public Pauta insertPauta(Pauta pauta) {

        return pautaRepository.save(pauta);
    }

    public Pauta getPauta(Long id) {
        Optional<Pauta> optPauta = pautaRepository.findById(id);
        return optPauta.orElseThrow(() ->
                new ObjectNotFoundException("Pauta de ID: " + id + " não encontrada"));
    }

    public static Pauta fromDTO(PautaDTO pautaDTO) {
        return Pauta.builder()
                .id(pautaDTO.getId())
                .name(pautaDTO.getName())
                .build();
    }
}
