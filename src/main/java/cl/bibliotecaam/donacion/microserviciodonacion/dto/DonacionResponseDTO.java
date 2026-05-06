package cl.bibliotecaam.donacion.microserviciodonacion.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonacionResponseDTO {

    private Long idDona;
    private Long numrunDona;
    private String dvrunDona;
    private String pnombreDona;
    private String snombreDona;
    private String appaternoDona;
    private String apmaternoDona;

    private Long numrunEmp;
}
