package cl.bibliotecaam.donacion.microserviciodonacion.controller;

import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionRequestDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionResponseDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import cl.bibliotecaam.donacion.microserviciodonacion.service.DonacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bibliotecaam/donacion")
@RequiredArgsConstructor
@Tag(name = "Donaciones", description = "Operaciones asociadas a donaciones.")
public class DonacionController {
    private final DonacionService donacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las donaciones", description = "Obtiene una lista de todas las donaciones-")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<List<DonacionResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(donacionService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener donacion por id", description = "Obtiene una donacion acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<DonacionResponseDTO> obtenerPorId(@PathVariable Long id){
        return donacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/run/{run}")
    @Operation(summary = "Obtener donacion por run", description = "Obtiene una donacion acorde a un run.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<List<DonacionResponseDTO>> obtenerPorRun(@PathVariable Long run) {
        return ResponseEntity.ok(donacionService.obtenerPorNumrun(run));
    }

    @GetMapping("/paterno/{apellido}")
    @Operation(summary = "Obtener donacion por apellido", description = "Obtiene una donacion acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<List<DonacionResponseDTO>> obtenerPorAppaterno(@PathVariable String apellido) {
        return ResponseEntity.ok(donacionService.obtenerPorAppaterno(apellido));
    }
    @GetMapping("/nombre/{pnombre}")
    @Operation(summary = "Obtener donacion por nombre", description = "Obtiene una donacion acorde a un nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<List<DonacionResponseDTO>> obtenerPorPnombre(@PathVariable String nombre) {
        return ResponseEntity.ok(donacionService.obtenerPorPnombre(nombre));
    }

    @PostMapping
    @Operation(summary = "Guardar una donacion", description = "Guarda una donacion acorde a lo ingresado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "400", description = "Error al ingresar parametros. Revise si ingreso todos los parametros solicitados."),
            @ApiResponse(responseCode = "403", description = "No tienes permiso para hacer el cambio.")
    })
    public ResponseEntity<DonacionResponseDTO> guardar(@Valid @RequestBody DonacionRequestDTO dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(201).body(donacionService.guardar(dto,token));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar donacion", description = "Actualiza una donacion acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donacion actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Donacion.class))),
            @ApiResponse(responseCode = "404", description = "El id de la donacion no existe.")
    })
    public ResponseEntity<DonacionResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody DonacionRequestDTO dto, @RequestHeader("Authorization") String token){
        return donacionService.actualizar(id, dto, token).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar donacion", description = "Elimina una donacion acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Donacion eliminada con exito!"),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El id de la donacion ingresada no existe!")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (donacionService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        donacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}