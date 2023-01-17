package com.caiotfernandes.assembleiaCoop.services;

import com.caiotfernandes.assembleiaCoop.domain.dtos.AssociadoDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Associado;
import com.caiotfernandes.assembleiaCoop.repositories.AssociadoRepository;
import com.caiotfernandes.assembleiaCoop.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    public Associado getAssociadoById(Long id) {
        Optional<Associado> opt = associadoRepository.findById(id);
        return opt.orElseThrow(() ->
                new ObjectNotFoundException("Associado de ID: " + id + " não encontrado"));
    }

    public Associado insertAssociado(Associado associado) {
        return associadoRepository.save(associado);
    }

    public static Associado fromDTO(AssociadoDTO associadoDTO) {
        return Associado.builder()
                .id(associadoDTO.getId())
                .name(associadoDTO.getName())
                .build();
    }
}
