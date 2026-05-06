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
                donacion.getId(),
                donacion.getNumrun(),
                donacion.getDv_run(),
                donacion.getPnombre(),
                donacion.getSnombre(),
                donacion.getAppaterno(),
                donacion.getApmaterno(),
                donacion.getEmpleado().getNumrun()
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
                .findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con el id: " + dto.getId()));
        Donacion donacion = new Donacion(
                null,
                dto.getNumrun(),
                dto.getDv_run(),
                dto.getPnombre(),
                dto.getSnombre(),
                dto.getAppaterno(),
                dto.getApmaterno(),
                empleado
        );
        return mapToDTO(donacionRepository.save(donacion));
    }

    public Optional<DonacionResponseDTO> actualizar(Long id, DonacionRequestDTO dto){
        return donacionRepository.findById(id).map( existente ->
        {
            Empleado empleado = empleadoRepository
                    .findById(dto.getId())
                    .orElseThrow(()-> new RuntimeException(
                            "Categoría no encontrada con id: " + dto.getId()));
            existente.setNumrun(dto.getNumrun());
            existente.setDv_run(dto.getDv_run());
            existente.setPnombre(dto.getPnombre());
            existente.setSnombre(dto.getSnombre());
            existente.setAppaterno(dto.getAppaterno());
            existente.setApmaterno(dto.getApmaterno());
            existente.setEmpleado(empleado);


            return mapToDTO(donacionRepository.save(existente));
        });
    }

    public void eliminar(Long id){
        donacionRepository.deleteById(id);
    }

    public List<DonacionResponseDTO> obtenerPorNumrun(Long numrun){
        return donacionRepository.findByNumrun(numrun)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DonacionResponseDTO> obtenerPorPnombre(String nombre){
        return donacionRepository.findByPnombre(nombre)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DonacionResponseDTO> obtenerPorAppaterno(String apellido){
        return donacionRepository.findByAppaterno(apellido)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}