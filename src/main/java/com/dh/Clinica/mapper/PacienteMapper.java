package com.dh.Clinica.mapper;

import com.dh.Clinica.dtos.PacienteDto;
import com.dh.Clinica.model.Paciente;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PacienteMapper {

    public Paciente toEntity(PacienteDto pacienteDto) {
        Paciente p = new Paciente();
        p.setApellido(pacienteDto.getApellido());
        p.setNombre(pacienteDto.getNombre());
        p.setDni(pacienteDto.getDni());
        p.setFechaAlta(pacienteDto.getFechaAlta());
        p.setDomicilio(pacienteDto.getDomicilio());

        return p;
    }
    public PacienteDto toDTO(Paciente p) {
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setApellido(p.getApellido());
        pacienteDto.setNombre(p.getNombre());
        pacienteDto.setDni(p.getDni());
        pacienteDto.setFechaAlta(p.getFechaAlta());
        pacienteDto.setDomicilio(p.getDomicilio());
        return pacienteDto;
    }


    public List<PacienteDto> obtenerPacientes(List<Paciente> pacientes) {
        List<PacienteDto> listaPacientesDto = new ArrayList<>();
        for (Paciente p : pacientes) {
            PacienteDto pacienteDtoCasteado = toDTO(p);
            listaPacientesDto.add(pacienteDtoCasteado);
        }
        return listaPacientesDto;
    }

    public Paciente setEntity(Paciente p, PacienteDto pacienteDto) {
        p.setApellido(pacienteDto.getApellido());
        p.setNombre(pacienteDto.getNombre());
        p.setDni(pacienteDto.getDni());
        p.setDomicilio(pacienteDto.getDomicilio());
        p.setFechaAlta(pacienteDto.getFechaAlta());
        return p;
    }
}
