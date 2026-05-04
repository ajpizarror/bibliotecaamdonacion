package cl.bibliotecaam.donacion.microserviciodonacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donacion")
public class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dona;
    @Column(nullable = false, length = 8, unique = true)
    private Long numrun_dona;
    @Column(nullable = false,length = 1, unique = false)
    private String dv_run;
    @Column(nullable = false,length = 30, unique = false)
    private String pnombre_dona;
    @Column(nullable = true,length = 30, unique = false)
    private String snombre_dona;
    @Column(nullable = false,length = 30, unique = false)
    private String appaterno_dona;
    @Column(nullable = true,length = 30, unique = false)
    private String apmaterno_dona;




}
