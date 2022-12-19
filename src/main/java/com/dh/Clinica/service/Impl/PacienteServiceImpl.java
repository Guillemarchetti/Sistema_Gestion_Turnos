package com.dh.Clinica.service.Impl;





import com.dh.Clinica.exceptions.ResourceNotFoundException;
import com.dh.Clinica.dtos.PacienteDto;
import com.dh.Clinica.mapper.PacienteMapper;
import com.dh.Clinica.model.Paciente;
import com.dh.Clinica.repository.IPacienteRepository;
import com.dh.Clinica.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements IService<PacienteDto> {

    private static final Logger log = Logger.getLogger(OdontologoServiceImpl.class);
    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;


    //Métodos
    public PacienteDto crear(PacienteDto pDto) {
        Paciente p = pacienteMapper.toEntity(pDto);
        Paciente pacienteGuardado = pacienteRepository.save(p);
        log.info("El paciente con DNI: " + pacienteGuardado.getDni() +" se creó de manera exitosa");
        return pacienteMapper.toDTO(pacienteGuardado);
    }

    @Override
    public List<PacienteDto> listar() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacienteMapper.obtenerPacientes(pacientes);
    }

    @Override
    public PacienteDto buscar(String dni) throws ResourceNotFoundException {
        Paciente p = pacienteRepository.buscarPorDni(dni);
        if ( p == null)
            throw new ResourceNotFoundException("No se encontró el paciente con DNI: " + dni);
        return pacienteMapper.toDTO(p);
    }

    @Override
    public PacienteDto actualizar(PacienteDto pacienteDto) throws ResourceNotFoundException {
        Paciente p = pacienteRepository.buscarPorDni(pacienteDto.getDni());
        if(p==null)
            throw new ResourceNotFoundException("No existe un paciente con DNI: " + pacienteDto.getDni());
        Paciente pacienteActualizado = pacienteMapper.setEntity(p, pacienteDto);
        Paciente pacienteGuardado = pacienteRepository.save(pacienteActualizado);
        log.info("El paciente con DNI: " + pacienteGuardado.getDni() +" se actualizó de manera exitosa");
        return pacienteMapper.toDTO(pacienteGuardado);
    }

    @Override
    public void eliminar(String dni) throws ResourceNotFoundException {
        Paciente p = pacienteRepository.buscarPorDni(dni);
        if(p==null)
            throw new ResourceNotFoundException("No existe un paciente con DNI: " + dni);
        pacienteRepository.delete(p);
        log.info("El paciente con DNI: " + dni +" se eliminó de manera exitosa");
    }


}
