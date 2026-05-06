package cl.bibliotecaam.donacion.microserviciodonacion.service;

import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionRequestDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionResponseDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import cl.bibliotecaam.donacion.microserviciodonacion.model.Empleado;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.DonacionRepository;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonacionService {

    private final DonacionRepository donacionRepository;
    private  final EmpleadoRepository empleadoRepository;

    private DonacionResponseDTO mapToDTO(Donacion donacion){
        return new DonacionResponseDTO(
                donacion.getIdDona(),
                donacion.getNumrunDona(),
                donacion.getDvrunDona(),
                donacion.getPnombreDona(),
                donacion.getSnombreDona(),
                donacion.getAppaternoDona(),
                donacion.getApmaternoDona(),
                donacion.getEmpleado().getNumrunEmp()
        );
    }

    public List<DonacionResponseDTO> obtenerTodos(){
        return donacionRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<DonacionResponseDTO> obtenerPorId(Long id){
        return donacionRepository.findById(id)
                .map(this::mapToDTO);
    }

    public DonacionResponseDTO guardar(DonacionRequestDTO dto){
        Empleado empleado = empleadoRepository
                .findById(dto.getIdEmp())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con el id: " + dto.getIdEmp()));
        Donacion donacion = new Donacion(
                null,
                dto.getNumrunDona(),
                dto.getDvrunDona(),
                dto.getPnombreDona(),
                dto.getSnombreDona(),
                dto.getAppaternoDona(),
                dto.getApmaternoDona(),
                empleado
        );
        return mapToDTO(donacionRepository.save(donacion));
    }

    public Optional<DonacionResponseDTO> actualizar(Long id, DonacionRequestDTO dto){
        return donacionRepository.findById(id).map( existente ->
        {
            Empleado empleado = empleadoRepository
                    .findById(dto.getIdEmp())
                    .orElseThrow(()-> new RuntimeException(
                            "Categoría no encontrada con id: " + dto.getIdEmp()));
            existente.setNumrunDona(dto.getNumrunDona());
            existente.setDvrunDona(dto.getDvrunDona());
            existente.setPnombreDona(dto.getPnombreDona());
            existente.setSnombreDona(dto.getSnombreDona());
            existente.setAppaternoDona(dto.getAppaternoDona());
            existente.setApmaternoDona(dto.getApmaternoDona());
            existente.setEmpleado(empleado);


            return mapToDTO(donacionRepository.save(existente));
        });
    }

    public void eliminar(Long id){
        donacionRepository.deleteById(id);
    }

    public List<DonacionResponseDTO> obtenerPorNumrun(Long numrun){
        return donacionRepository.findByNumrunDona(numrun)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DonacionResponseDTO> obtenerPorPnombre(String nombre){
        return donacionRepository.findByPnombreDona(nombre)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DonacionResponseDTO> obtenerPorAppaterno(String apellido){
        return donacionRepository.findByAppaternoDona(apellido)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}