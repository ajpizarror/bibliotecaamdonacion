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
    private Long id;
    @Column(nullable = false, length = 8, unique = true)
    private Long numrun;
    @Column(nullable = false,length = 1, unique = false)
    private String dvRun;
    @Column(nullable = false,length = 30, unique = false)
    private String pnombre;
    @Column(nullable = true,length = 30, unique = false)
    private String snombre;
    @Column(nullable = false,length = 30, unique = false)
    private String appaterno;
    @Column(nullable = true,length = 30, unique = false)
    private String apmaterno;

    @Column(nullable = false)
    private Long idEmpleado;




}
