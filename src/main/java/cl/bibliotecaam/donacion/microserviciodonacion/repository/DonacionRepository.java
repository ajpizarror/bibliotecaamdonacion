package cl.bibliotecaam.donacion.microserviciodonacion.repository;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {

    List<Donacion> findByNumrun_dona(Long numrun);

    List<Donacion> findByPnombre_dona(String pnombre);

    List<Donacion> findByAppaterno_dona(String appaterno);

}
