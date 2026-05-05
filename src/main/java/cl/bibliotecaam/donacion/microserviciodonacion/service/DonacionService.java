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
                donacion.getId_dona(),
                donacion.getNumrun_dona(),
                donacion.getDv_run(),
                donacion.getPnombre_dona(),
                donacion.getSnombre_dona(),
                donacion.getAppaterno_dona(),
                donacion.getApmaterno_dona(),
                donacion.getEmpleado().getNumrun_emp()
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
                .findById(dto.getId_emp())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con el id: " + dto.getId_emp()));
        Donacion donacion = new Donacion(
                null,
                dto.getNumrun_dona(),
                dto.getDv_run(),
                dto.getPnombre_dona(),
                dto.getSnombre_dona(),
                dto.getAppaterno_dona(),
                dto.getApmaterno_dona(),
                empleado
        );
        return mapToDTO(donacionRepository.save(donacion));
    }

    public Optional<DonacionResponseDTO> actualizar(Long id, DonacionRequestDTO dto){
        return donacionRepository.findById(id).map( existente ->
        {
            Empleado empleado = empleadoRepository
                    .findById(dto.getId_emp())
                    .orElseThrow(()-> new RuntimeException(
                            "Categoría no encontrada con id: " + dto.getId_emp()));
            existente.setNumrun_dona(dto.getNumrun_dona());
            existente.setDv_run(dto.getDv_run());
            existente.setPnombre_dona(dto.getPnombre_dona());
            existente.setSnombre_dona(dto.getSnombre_dona());
            existente.setAppaterno_dona(dto.getAppaterno_dona());
            existente.setApmaterno_dona(dto.getApmaterno_dona());
            existente.setEmpleado(empleado);


            return mapToDTO(donacionRepository.save(existente));
        });
    }

    public void eliminar(Long id){
        donacionRepository.deleteById(id);
    }

    public List<DonacionResponseDTO> obtenerPorNumrun(Long numrun){
        return donacionRepository.findByNumrun_dona(numrun)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DonacionResponseDTO> obtenerPorPnombre(String nombre){
        return donacionRepository.findByPnombre_dona(nombre)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DonacionResponseDTO> obtenerPorAppaterno(String apellido){
        return donacionRepository.findbyAppaterno_dona(apellido)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}