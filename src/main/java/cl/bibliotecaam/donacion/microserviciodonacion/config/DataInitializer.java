package cl.bibliotecaam.donacion.microserviciodonacion.config;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import cl.bibliotecaam.donacion.microserviciodonacion.model.Empleado;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.DonacionRepository;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.EmpleadoRepository;
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

    private final EmpleadoRepository empleadoRepository;

    @Override
    public void run(String... args){
        if (donacionRepository.count()>0){
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        Empleado operario = empleadoRepository.save(
                new Empleado(null,17752427L,"1","Alfonso","Jose","Pizarro","Ramirez", LocalDate.of(1990,11,15), 280000L));
        Empleado gerente = empleadoRepository.save(
                new Empleado(null,12345678L,"K","Martina","Soledad","Pizarro","Ramirez",LocalDate.of(1988,9,21),800000L));

        donacionRepository.save(new Donacion(null, 12345678L, "K", "Jose", "Joaquin", "Escobar","Pizarro",operario));
        donacionRepository.save(new Donacion(null, 22174909L, "4", "Maikol", "Marmol", "Jaime","Navarro",gerente));

    }
}
