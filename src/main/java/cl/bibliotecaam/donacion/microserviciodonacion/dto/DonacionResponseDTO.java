package cl.bibliotecaam.donacion.microserviciodonacion.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonacionResponseDTO {

    private Long id_dona;
    private Long numrun_dona;
    private String dvrun_dona;
    private String pnombre_dona;
    private String snombre_dona;
    private String appaterno_dona;
    private String apmaterno_dona;

    private Long numrun_emp;
}
