package com.caiotfernandes.assembleiaCoop.services;

import com.caiotfernandes.assembleiaCoop.repositories.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {
    
    @Autowired
    PautaRepository pautaRepository;

}
