package com.dh.Clinica.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private int id;
    private String dni;
    private String nombre;
    private String apellido;

    private String domicilio;


    private LocalDate fechaAlta;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Turno> turnos;




}
