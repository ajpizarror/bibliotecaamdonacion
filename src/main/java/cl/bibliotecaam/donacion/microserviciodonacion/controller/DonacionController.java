package cl.bibliotecaam.donacion.microserviciodonacion.controller;

import cl.bibliotecaam.donacion.microserviciodonacion.assemblers.DonacionModelAssembler;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.MediaTypes;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/bibliotecaam/donacion")
@RequiredArgsConstructor
@Tag(name = "Donaciones", description = "Operaciones asociadas a donaciones.")
public class DonacionController {
    private final DonacionService donacionService;

    @Autowired
    private final DonacionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las donaciones", description = "Obtiene una lista de todas las donaciones-")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<CollectionModel<EntityModel<DonacionResponseDTO>>> obtenerTodos(){
        List<EntityModel<DonacionResponseDTO>> donaciones = donacionService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(donaciones,
                linkTo(methodOn(DonacionController.class).obtenerTodos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener donacion por id", description = "Obtiene una donacion acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<EntityModel<DonacionResponseDTO>> obtenerPorId(@PathVariable Long id){
        return donacionService.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/run/{run}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener donacion por run", description = "Obtiene una donacion acorde a un run.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<CollectionModel<EntityModel<DonacionResponseDTO>>> obtenerPorRun(@PathVariable Long run) {
        List<EntityModel<DonacionResponseDTO>> donaciones = donacionService.obtenerPorNumrun(run).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(donaciones,
                linkTo(methodOn(DonacionController.class).obtenerPorRun(run)).withSelfRel()));
    }

    @GetMapping("/paterno/{apellido}")
    @Operation(summary = "Obtener donacion por apellido", description = "Obtiene una donacion acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<CollectionModel<EntityModel<DonacionResponseDTO>>> obtenerPorAppaterno(@PathVariable String apellido) {
        List<EntityModel<DonacionResponseDTO>> donaciones = donacionService.obtenerPorAppaterno(apellido).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(donaciones,
                linkTo(methodOn(DonacionController.class).obtenerPorAppaterno(apellido)).withSelfRel()));
    }

    @GetMapping("/nombre/{pnombre}")
    @Operation(summary = "Obtener donacion por nombre", description = "Obtiene una donacion acorde a un nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Donacion no encontrada")
    })
    public ResponseEntity<CollectionModel<EntityModel<DonacionResponseDTO>>> obtenerPorPnombre(@PathVariable String nombre) {
        List<EntityModel<DonacionResponseDTO>> donaciones = donacionService.obtenerPorPnombre(nombre).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(donaciones,
                linkTo(methodOn(DonacionController.class).obtenerPorPnombre(nombre)).withSelfRel()));      }

    @PostMapping
    @Operation(summary = "Guardar una donacion", description = "Guarda una donacion acorde a lo ingresado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "400", description = "Error al ingresar parametros. Revise si ingreso todos los parametros solicitados."),
            @ApiResponse(responseCode = "403", description = "No tienes permiso para hacer el cambio.")
    })
    public ResponseEntity<EntityModel<DonacionResponseDTO>> guardar(@Valid @RequestBody DonacionRequestDTO dto, @RequestHeader("Authorization") String token){
        DonacionResponseDTO nuevaDonacion = donacionService.guardar(dto, token);

        return ResponseEntity.status(201).body(assembler.toModel(nuevaDonacion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar donacion", description = "Actualiza una donacion acorde a una id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donacion actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Donacion.class))),
            @ApiResponse(responseCode = "404", description = "El id de la donacion no existe.")
    })
    public ResponseEntity<EntityModel<DonacionResponseDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody DonacionRequestDTO dto, @RequestHeader("Authorization") String token){
        return donacionService.actualizar(id, dto, token)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());    }

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