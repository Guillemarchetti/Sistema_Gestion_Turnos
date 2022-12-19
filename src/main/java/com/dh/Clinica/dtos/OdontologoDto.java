package com.dh.Clinica.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OdontologoDto {

    @NotNull
    private String apellido;
    @NotNull
    private String nombre;
    @NotNull
    private String matricula;


}
