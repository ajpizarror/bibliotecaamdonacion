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
    private Long numrunDona;
    @NotBlank(message = "El DV es obligatorio.")
    private String dvrunDona;
    @NotBlank(message = "El primer nombre es obligatorio.")
    private String pnombreDona;
    private String snombreDona;
    @NotBlank(message = "El apellido paterno es obligatorio.")
    private String appaternoDona;
    @NotBlank(message = "El apellido materno es obligatorio.")
    private String apmaternoDona;

    @NotNull(message = "La donacion tuvo que ser supervisada por un empleado.")
    private Long idEmp;

}
