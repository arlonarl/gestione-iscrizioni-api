package com.example.progettofinale.core.config;

import com.example.progettofinale.domain.repository.CorsoRepository;
import com.example.progettofinale.domain.repository.DocenteRepository;
import com.example.progettofinale.domain.repository.IscrizioneRepository;
import com.example.progettofinale.domain.repository.StudenteRepository;
import com.example.progettofinale.domain.service.CorsoService;
import com.example.progettofinale.domain.service.DocenteService;
import com.example.progettofinale.domain.service.IscrizioneService;
import com.example.progettofinale.domain.service.StudenteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig {
    @Bean
    public StudenteService studenteService(StudenteRepository studenteRepository) {
        return new StudenteService(studenteRepository);
    }

    @Bean
    public CorsoService corsoService(CorsoRepository corsoRepository, DocenteRepository docenteRepository) {
        return new CorsoService(corsoRepository, docenteRepository);
    }

    @Bean
    public DocenteService docenteService(DocenteRepository docenteRepository, CorsoRepository corsoRepository) {
        return new DocenteService(docenteRepository, corsoRepository);
    }

    @Bean
    public IscrizioneService iscrizioneService(
            IscrizioneRepository iscrizioneRepository,
            StudenteRepository studenteRepository,
            CorsoRepository corsoRepository
    ) {
        return new IscrizioneService(iscrizioneRepository, studenteRepository, corsoRepository);
    }
}
