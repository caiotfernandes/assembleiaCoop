package com.caiotfernandes.assembleiaCoop.config;

import com.caiotfernandes.assembleiaCoop.domain.entities.Associado;
import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import com.caiotfernandes.assembleiaCoop.repositories.AssociadoRepository;
import com.caiotfernandes.assembleiaCoop.repositories.PautaRepository;
import com.caiotfernandes.assembleiaCoop.repositories.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Profile("!test")
@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        associadoRepository.deleteAll();
        pautaRepository.deleteAll();
        sessaoRepository.deleteAll();

        Associado a1 = new Associado(null, "José");
        Associado a2 = new Associado(null, "Maria");
        Associado a3 = new Associado(null, "Joana");

        associadoRepository.saveAll(Arrays.asList(a1,a2,a3));

        Pauta p1 = new Pauta(null, "Ponto online");
        Pauta p2 = new Pauta(null, "Homeoffice");
        Pauta p3 = new Pauta(null, "Trabalho híbrido");

        pautaRepository.saveAll(Arrays.asList(p1,p2,p3));

    }
}
