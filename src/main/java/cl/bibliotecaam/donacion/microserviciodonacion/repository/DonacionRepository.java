package cl.bibliotecaam.donacion.microserviciodonacion.repository;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    @Query("SELECT d FROM Donacion d WHERE d.numrun = :numrun")
    List<Donacion> findByNumrun(@Param("numrun") Long numrun);
    @Query("SELECT d FROM Donacion d WHERE d.pnombre = :nombre")
    List<Donacion> findByPnombre(@Param("nombre") String nombre);
    @Query("SELECT d FROM Donacion d WHERE d.appaterno = :apellido")
    List<Donacion> findByAppaterno(@Param("apellido") String apellido);
}
