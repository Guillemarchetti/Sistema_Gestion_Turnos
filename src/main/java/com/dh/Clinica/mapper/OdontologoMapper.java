package com.dh.Clinica.mapper;

import com.dh.Clinica.dtos.OdontologoDto;
import com.dh.Clinica.model.Odontologo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OdontologoMapper {

    public Odontologo toEntity(OdontologoDto odontologoDto) {
        Odontologo o = new Odontologo();
        o.setApellido(odontologoDto.getApellido());
        o.setNombre(odontologoDto.getNombre());
        o.setMatricula(odontologoDto.getMatricula());
        return o;
    }
    public OdontologoDto toDTO(Odontologo o) {
        OdontologoDto odontologoDto = new OdontologoDto();
        odontologoDto.setApellido(o.getApellido());
        odontologoDto.setNombre(o.getNombre());
        odontologoDto.setMatricula(o.getMatricula());
        return odontologoDto;
    }


    public List<OdontologoDto> obtenerOdontologos(List<Odontologo> odontologos) {
        List<OdontologoDto> listaOdontologosDto = new ArrayList<>();
        for (Odontologo o : odontologos) {
            OdontologoDto odontologoDtoCasteado = toDTO(o);
            listaOdontologosDto.add(odontologoDtoCasteado);
        }
        return listaOdontologosDto;
    }

    public Odontologo setEntity(Odontologo o, OdontologoDto odontologoDto) {
        o.setApellido(odontologoDto.getApellido());
        o.setNombre(odontologoDto.getNombre());
        o.setMatricula(odontologoDto.getMatricula());
        return o;
    }
}
