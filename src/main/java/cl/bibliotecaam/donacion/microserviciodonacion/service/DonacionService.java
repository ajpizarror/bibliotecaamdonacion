package cl.bibliotecaam.donacion.microserviciodonacion.service;

import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionRequestDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionResponseDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.DonacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonacionService {

    private final DonacionRepository donacionRepository;

    private final WebClient webClient;

    private DonacionResponseDTO mapToDTO(Donacion donacion){
        return new DonacionResponseDTO(
                donacion.getId(),
                donacion.getNumrun(),
                donacion.getDvRun(),
                donacion.getPnombre(),
                donacion.getSnombre(),
                donacion.getAppaterno(),
                donacion.getApmaterno(),
                donacion.getIdEmpleado()
        );
    }


    /*
    -------------------------VALIDANDO EL ID EMPLEADO -----------------------
     */
    private void validarEmpleado(Long empleadoId, String token) {
        try {
            webClient.get()
                    .uri("/api/bibliotecaam/empleado/{id}", empleadoId)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info(">>> Empleado {} validado correctamente (WebClient) id:", empleadoId);

        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException(
                    "El empleado/empleada con id " + empleadoId + " no existe en la base de datos en Empleado.");
        } catch (WebClientResponseException.BadRequest br){
            throw new RuntimeException(
                    "El empleado/empleada con id " + empleadoId + " no existe en la base de datos de Empleado.");
        } catch (Exception e) {
            throw new RuntimeException(
                    "No se puede conectar con Empleado: " + e.getMessage());
        }
    }

    public List<DonacionResponseDTO> obtenerTodos(){
        return donacionRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<DonacionResponseDTO> obtenerPorId(Long id){
        return donacionRepository.findById(id)
                .map(this::mapToDTO);
    }

    public DonacionResponseDTO guardar(DonacionRequestDTO dto, String token){
        validarEmpleado(dto.getIdEmpleado(),token);
        Donacion donacion = new Donacion(
                null,
                dto.getNumrun(),
                dto.getDvRun(),
                dto.getPnombre(),
                dto.getSnombre(),
                dto.getAppaterno(),
                dto.getApmaterno(),
                dto.getIdEmpleado()
        );
        return mapToDTO(donacionRepository.save(donacion));
    }

    public Optional<DonacionResponseDTO> actualizar(Long id, DonacionRequestDTO dto, String token){
        return donacionRepository.findById(id).map( existente ->
        {
            validarEmpleado(dto.getIdEmpleado(), token);
            existente.setNumrun(dto.getNumrun());
            existente.setDvRun(dto.getDvRun());
            existente.setPnombre(dto.getPnombre());
            existente.setSnombre(dto.getSnombre());
            existente.setAppaterno(dto.getAppaterno());
            existente.setApmaterno(dto.getApmaterno());
            existente.setIdEmpleado(dto.getIdEmpleado());

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