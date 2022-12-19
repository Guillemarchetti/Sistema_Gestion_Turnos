package com.dh.Clinica.dtos;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDto {
    @NotNull
    private String apellido;
    @NotNull
    private String nombre;
    @NotNull
    private String dni;
    @NotNull
    private String domicilio;
    @NotNull
    private LocalDate fechaAlta;




}
