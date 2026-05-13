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
    private Long numrun;

    @NotBlank(message = "El DV es obligatorio.")
    private String dv_run;

    @NotBlank(message = "El primer nombre es obligatorio.")
    private String pnombre;

    private String snombre;

    @NotBlank(message = "El apellido paterno es obligatorio.")
    private String appaterno;

    @NotBlank(message = "El apellido materno es obligatorio.")
    private String apmaterno;

    @NotNull(message = "La donacion tuvo que ser supervisada por un empleado (idEmpleado).")
    private Long idEmpleado;

}
