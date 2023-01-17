package com.caiotfernandes.assembleiaCoop.resources;

import com.caiotfernandes.assembleiaCoop.domain.dtos.PautaDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import com.caiotfernandes.assembleiaCoop.services.PautaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaDTO> insertPauta(@RequestBody @Valid PautaDTO pautaDTO) {
        Pauta pauta = pautaService.insertPauta(PautaService.fromDTO(pautaDTO));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pauta.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDTO> getPauta(@PathVariable Long id) {
        Pauta pauta = pautaService.getPauta(id);
        return ResponseEntity.ok().body(PautaDTO.fromPauta(pauta));
    }

}
