package cl.bibliotecaam.donacion.microserviciodonacion;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.DonacionRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private DonacionRepository donacionRepository;

    @Override
    public void run (String... args) throws Exception{
        Faker faker = new Faker();

        for (int i = 0; i < 4; i++) {
            Donacion donacion = new Donacion();
            donacion.setNumrun((long) faker.number().numberBetween(10000000, 99999999));
            String opcionesDv = "0123456789K";
            int indice = faker.number().numberBetween(0, opcionesDv.length());
            String dvAleatorio = String.valueOf(opcionesDv.charAt(indice));
            donacion.setDvRun(dvAleatorio);
            donacion.setPnombre(faker.name().firstName());
            donacion.setSnombre(faker.name().firstName());
            donacion.setAppaterno(faker.name().lastName());
            donacion.setApmaterno(faker.name().lastName());
            donacion.setIdEmpleado((long) faker.number().numberBetween(1,3));
            donacionRepository.save(donacion);
        }
    }
}
