package cl.bibliotecaam.donacion.microserviciodonacion.service;

import cl.bibliotecaam.donacion.microserviciodonacion.model.Empleado;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodos(){
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerPorId(Long id){
        return empleadoRepository.findById(id);
    }

    public Empleado guardar(Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    public void eliminar(Long id){
        empleadoRepository.deleteById(id);
    }
}
