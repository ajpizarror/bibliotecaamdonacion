package cl.bibliotecaam.donacion.microserviciodonacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonacionResponseDTO {
    private Long id;
    private Long numrun;
    private String dvRun;
    private String pnombre;
    private String snombre;
    private String appaterno;
    private String apmaterno;

    private Long idEmpleado;
}
