package cl.bibliotecaam.donacion.microserviciodonacion.repository;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {

    List<Donacion> findByNumrunDona(Long numrun);

    List<Donacion> findByPnombreDona(String pnombre);

    List<Donacion> findByAppaternoDona(String appaterno);

}
