package com.dh.Clinica.service.Impl;

import com.dh.Clinica.dtos.TurnoDto;
import com.dh.Clinica.exceptions.BadRequestException;
import com.dh.Clinica.exceptions.ResourceNotFoundException;
import com.dh.Clinica.mapper.OdontologoMapper;
import com.dh.Clinica.mapper.TurnoMapper;
import com.dh.Clinica.model.Odontologo;
import com.dh.Clinica.model.Turno;
import com.dh.Clinica.repository.IOdontologoRepository;
import com.dh.Clinica.repository.IPacienteRepository;
import com.dh.Clinica.repository.ITurnoRepository;
import com.dh.Clinica.service.IServiceTurno;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TurnoServiceImpl implements IServiceTurno {

    private static final Logger log = Logger.getLogger(TurnoServiceImpl.class);

    @Autowired
    private OdontologoServiceImpl odontologoService;

    @Autowired
    private OdontologoMapper odontologoMapper;

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private TurnoMapper turnoMapper;

    @Override
    public TurnoDto crear(TurnoDto tDto) throws BadRequestException, ResourceNotFoundException {
        if(pacienteRepository.buscarPorDni(tDto.getDni()) == null)
                throw new ResourceNotFoundException("El paciente seleccionado no se encuentran en nuestra base de datos");
        if(odontologoRepository.buscarPorMatricula(tDto.getMatricula()) == null)
            throw new ResourceNotFoundException("El odontologo seleccionado no se encuentran en nuestra base de datos");
        if(!turnoDisponible(tDto))
                throw new BadRequestException("El turno ya se encuentra ocupado");
        Turno t = turnoMapper.toEntity(tDto);
        Turno turnoGuardado = turnoRepository.save(t);
        log.info("El turno con id" + turnoGuardado.getId() + " ha sido guardado en la base de datos");
        return turnoMapper.toDTO(turnoGuardado);
    }

    @Override
    public List<TurnoDto> listar() {
        List<Turno> turnos = turnoRepository.findAll();
        return turnoMapper.obtenerTurnos(turnos);
    }

    @Override
    public List<TurnoDto> buscar(String dni) throws ResourceNotFoundException {
        List<Turno> turnos = turnoRepository.buscarTurnosPorDni(dni);
        if(turnos==null)
            throw new ResourceNotFoundException("No existen turnos para el paciente con DNI: " + dni);
        return turnoMapper.obtenerTurnos(turnos);
    }

    @Override
    public TurnoDto actualizar(int id,TurnoDto tDto) throws ResourceNotFoundException {
        Turno t = turnoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No existe un turno con id: " + id));
        tDto.setId(id);
        if(!turnoDisponible(tDto))
            throw new ResourceNotFoundException("Ya existe un turno asignado para esa fecha y ese horario");
        Turno turnoActualizado = turnoMapper.setEntity(t,tDto);
        Turno turnoGuardado = turnoRepository.save(turnoActualizado);
        log.info("El turno con id " + turnoGuardado.getId() + " ha sido actualizado en la base de datos");
        return turnoMapper.toDTO(turnoGuardado);
    }

    @Override
    public void eliminar(int id) throws ResourceNotFoundException {
        Turno t = turnoRepository.buscarPorId(id);
        if(t == null)
            throw new ResourceNotFoundException("No existe turno con el id: " + id);
        turnoRepository.delete(t);
        log.info("El turno con id " + t.getId() + " ha sido eliminado de la base de datos");
    }


    public boolean turnoDisponible(TurnoDto tDto) {
        boolean estaDisponible = true;
        Odontologo o= odontologoRepository.buscarPorMatricula(tDto.getMatricula());
        List<Turno> turnos = turnoRepository.turnosPorOdontologo(o.getMatricula());
        if (turnos != null) {
            for (Turno turno : turnos) {
                if (turno.getFecha().equals(tDto.getFecha()) && turno.getHora().equals(tDto.getHora())) {
                    estaDisponible = false;
                    break;
                }
            }
        }
        return estaDisponible;
    }



}
