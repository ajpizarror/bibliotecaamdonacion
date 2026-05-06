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
    private Long idDona;
    @Column(nullable = false, length = 8, unique = true)
    private Long numrunDona;
    @Column(nullable = false,length = 1, unique = false)
    private String dvrunDona;
    @Column(nullable = false,length = 30, unique = false)
    private String pnombreDona;
    @Column(nullable = true,length = 30, unique = false)
    private String snombreDona;
    @Column(nullable = false,length = 30, unique = false)
    private String appaternoDona;
    @Column(nullable = true,length = 30, unique = false)
    private String apmaternoDona;

    @ManyToOne
    @JoinColumn(name = "id_emp", nullable = true)
    private Empleado empleado;




}
