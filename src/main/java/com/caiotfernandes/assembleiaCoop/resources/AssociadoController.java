package com.caiotfernandes.assembleiaCoop.resources;

import com.caiotfernandes.assembleiaCoop.domain.dtos.AssociadoDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Associado;
import com.caiotfernandes.assembleiaCoop.services.AssociadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/associados")
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;

    @PostMapping
    public ResponseEntity<AssociadoDTO> insertAssociado(@RequestBody @Valid AssociadoDTO associadoDTO) {
        Associado associado = associadoService.insertAssociado(AssociadoService.fromDTO(associadoDTO));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(associado.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDTO> getAssociado(@PathVariable Long id) {
        Associado associado = associadoService.getAssociadoById(id);
        return ResponseEntity.ok().body(AssociadoDTO.fromAssociado(associado));
    }
}
