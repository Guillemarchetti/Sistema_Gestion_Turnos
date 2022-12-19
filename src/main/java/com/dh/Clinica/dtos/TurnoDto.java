package com.dh.Clinica.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDto {


    private int id;
    @NotNull
    private LocalDate fecha;
    @NotNull
    private LocalTime hora;
    @NotNull
    private String nombreOd;
    @NotNull
    private String apellidoOd;
    @NotNull
    private String matricula;
    @NotNull
    private String nombrePac;
    @NotNull
    private String apellidoPac;
    @NotNull
    private String dni;

}
