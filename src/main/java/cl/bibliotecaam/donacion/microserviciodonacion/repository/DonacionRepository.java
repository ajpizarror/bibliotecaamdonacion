package cl.bibliotecaam.donacion.microserviciodonacion.repository;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    @Query("SELECT d FROM Donacion d WHERE UPPER(d.numrun) LIKE UPPER(CONCAT('%', :numrun, '%'))")
    List<Donacion> findByNumrun(@Param("numrun") Long numrun);

    @Query("SELECT d FROM Donacion d WHERE UPPER(d.pnombre) LIKE UPPER(CONCAT('%',:nombre,'%'))")
    List<Donacion> findByPnombre(@Param("nombre") String nombre);

    @Query("SELECT d FROM Donacion d WHERE UPPER(d.appaterno) = UPPER(CONCAT('%',:apellido,'%'))")
    List<Donacion> findByAppaterno(@Param("apellido") String apellido);

}
