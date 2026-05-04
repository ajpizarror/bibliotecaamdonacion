package cl.bibliotecaam.donacion.microserviciodonacion.repository;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
