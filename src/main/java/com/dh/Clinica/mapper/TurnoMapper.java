package com.dh.Clinica.mapper;

import com.dh.Clinica.dtos.TurnoDto;
import com.dh.Clinica.model.Odontologo;
import com.dh.Clinica.model.Paciente;
import com.dh.Clinica.model.Turno;
import com.dh.Clinica.repository.IOdontologoRepository;
import com.dh.Clinica.repository.IPacienteRepository;
import com.dh.Clinica.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TurnoMapper {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    private ITurnoRepository turnoRepository;

    public Turno toEntity(TurnoDto turnoDto) {
        Paciente p = pacienteRepository.buscarPorDni(turnoDto.getDni());
        Odontologo o = odontologoRepository.buscarPorMatricula(turnoDto.getMatricula());
        Turno t = new Turno();
        t.setId(turnoDto.getId());
        t.setFecha(turnoDto.getFecha());
        t.setHora(turnoDto.getHora());
        t.setPaciente(p);
        t.setOdontologo(o);
        return t;
    }
    public TurnoDto toDTO(Turno t) {
        TurnoDto turnoDto = new TurnoDto();
        turnoDto.setId(t.getId());
        turnoDto.setFecha(t.getFecha());
        turnoDto.setHora(t.getHora());
        turnoDto.setNombreOd(t.getOdontologo().getNombre());
        turnoDto.setApellidoOd(t.getOdontologo().getApellido());
        turnoDto.setMatricula(t.getOdontologo().getMatricula());
        turnoDto.setNombrePac(t.getPaciente().getNombre());
        turnoDto.setApellidoPac(t.getPaciente().getApellido());
        turnoDto.setDni(t.getPaciente().getDni());
        return turnoDto;
    }


    public List<TurnoDto> obtenerTurnos(List<Turno> turnos) {
        List<TurnoDto> listaTurnosDto = new ArrayList<>();
        for (Turno t : turnos) {
            TurnoDto turnoDto = toDTO(t);
            listaTurnosDto.add(turnoDto);
        }
        return listaTurnosDto;
    }

    public Turno setEntity(Turno t, TurnoDto turnoDto) {
        Paciente p = pacienteRepository.buscarPorDni(turnoDto.getDni());
        Odontologo o = odontologoRepository.buscarPorMatricula(turnoDto.getMatricula());
        //t.setId(turnoDto.getId());
        t.setFecha(turnoDto.getFecha());
        t.setHora(turnoDto.getHora());
        t.setPaciente(p);
        t.setOdontologo(o);
        return t;
    }
}
