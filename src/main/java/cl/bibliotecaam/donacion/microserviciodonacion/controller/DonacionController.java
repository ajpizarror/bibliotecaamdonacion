package cl.bibliotecaam.donacion.microserviciodonacion.controller;

import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionRequestDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionResponseDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.service.DonacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bibliotecaam/donacion")
@RequiredArgsConstructor
public class DonacionController {
    private final DonacionService donacionService;

    @GetMapping
    public ResponseEntity<List<DonacionResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(donacionService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonacionResponseDTO> obtenerPorId(@PathVariable Long id){
        return donacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<List<DonacionResponseDTO>> obtenerPorRun(@PathVariable Long run) {
        return ResponseEntity.ok(donacionService.obtenerPorNumrun(run));
    }

    @GetMapping("/paterno/{apellido}")
    public ResponseEntity<List<DonacionResponseDTO>> obtenerPorAppaterno(@PathVariable String apellido) {
        return ResponseEntity.ok(donacionService.obtenerPorAppaterno(apellido));
    }
    @GetMapping("/nombre/{pnombre}")
    public ResponseEntity<List<DonacionResponseDTO>> obtenerPorPnombre(@PathVariable String nombre) {
        return ResponseEntity.ok(donacionService.obtenerPorPnombre(nombre));
    }

    @PostMapping
    public ResponseEntity<DonacionResponseDTO> guardar(@Valid @RequestBody DonacionRequestDTO dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(201).body(donacionService.guardar(dto,token));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonacionResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody DonacionRequestDTO dto, @RequestHeader("Authorization") String token){
        return donacionService.actualizar(id, dto, token).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (donacionService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        donacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}