package com.caiotfernandes.assembleiaCoop.resources;

import com.caiotfernandes.assembleiaCoop.domain.dtos.SessaoDTO;
import com.caiotfernandes.assembleiaCoop.domain.entities.Sessao;
import com.caiotfernandes.assembleiaCoop.services.SessaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/sessao")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping
    public ResponseEntity<Sessao> openSession(@RequestBody @Valid SessaoDTO sessaoDTO) {
        Sessao sessao = sessaoService.openSession(sessaoDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sessao.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> getSession(@PathVariable Long id) {
        return ResponseEntity.ok().body(sessaoService.getSessionById(id));
    }

}
