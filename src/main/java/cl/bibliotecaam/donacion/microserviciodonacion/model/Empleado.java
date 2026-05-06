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
    private Long id_emp;

    @Column(nullable = false,precision = 8)
    private Long numrun_emp;

    @Column(nullable = false, length = 1)
    private String dvrun_emp;

    @Column(nullable = false, length = 30)
    private String pnombre_emp;

    @Column(nullable = true, length = 30)
    private String snombre_emp;

    @Column(nullable = false, length = 30)
    private String appaterno_emp;

    @Column(nullable = false, length = 30)
    private String apmaterno_emp;

    @Column(nullable = false)
    private LocalDate fec_contrato;

    @Column(nullable = false, precision = 7)
    private Long sueldo;

}
