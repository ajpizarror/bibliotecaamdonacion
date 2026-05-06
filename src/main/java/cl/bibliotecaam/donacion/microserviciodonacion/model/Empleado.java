package cl.bibliotecaam.donacion.microserviciodonacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmp;

    @Column(nullable = false,precision = 8)
    private Long numrunEmp;

    @Column(nullable = false, length = 1)
    private String dvrunEmp;

    @Column(nullable = false, length = 30)
    private String pnombreEmp;

    @Column(nullable = true, length = 30)
    private String snombreEmp;

    @Column(nullable = false, length = 30)
    private String appaternoEmp;

    @Column(nullable = false, length = 30)
    private String apmaternoEmp;

    @Column(nullable = false)
    private LocalDate fecContrato;

    @Column(nullable = false, precision = 7)
    private Long sueldo;

}
