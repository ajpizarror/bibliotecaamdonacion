package cl.bibliotecaam.donacion.microserviciodonacion.config;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.DonacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final DonacionRepository donacionRepository;

    @Override
    public void run(String... args){
        if (donacionRepository.count()>0){
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        donacionRepository.save(new Donacion(null, 12345678L, "K", "Jose", "Joaquin", "Escobar","Pizarro",1L));
        donacionRepository.save(new Donacion(null, 22174909L, "4", "Maikol", "Marmol", "Jaime","Navarro",2L));

    }
}