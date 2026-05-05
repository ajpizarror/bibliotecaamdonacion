package cl.bibliotecaam.donacion.microserviciodonacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionRequestDTO {

    @NotNull(message = "El run es obligatorio.")
    private Long numrun_dona;
    @NotBlank(message = "El DV es obligatorio.")
    private String dv_run;
    @NotBlank(message = "El primer nombre es obligatorio.")
    private String pnombre_dona;
    private String snombre_dona;
    @NotBlank(message = "El apellido paterno es obligatorio.")
    private String appaterno_dona;
    @NotBlank(message = "El apellido materno es obligatorio.")
    private String apmaterno_dona;

    @NotNull(message = "La donacion tuvo que ser supervisada por un empleado.")
    private Long id_emp;

}
