package com.dh.Clinica.service.Impl;



import com.dh.Clinica.exceptions.ResourceNotFoundException;
import com.dh.Clinica.dtos.OdontologoDto;
import com.dh.Clinica.mapper.OdontologoMapper;
import com.dh.Clinica.model.Odontologo;
import com.dh.Clinica.repository.IOdontologoRepository;
import com.dh.Clinica.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OdontologoServiceImpl implements IService<OdontologoDto> {

    private static final Logger log = Logger.getLogger(OdontologoServiceImpl.class);

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    private OdontologoMapper odontologoMapper;


    //Métodos
    public OdontologoDto crear(OdontologoDto oDto) {
        Odontologo o = odontologoMapper.toEntity(oDto);
        Odontologo odontologoGuardado = odontologoRepository.save(o);
        log.info("El odontologo con matricula: " + odontologoGuardado.getMatricula() +" se creó de manera exitosa");
        return odontologoMapper.toDTO(odontologoGuardado);
    }

    @Override
    public List<OdontologoDto> listar() {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        return odontologoMapper.obtenerOdontologos(odontologos);
    }

    @Override
    public OdontologoDto buscar(String matricula) throws ResourceNotFoundException {
        Odontologo o = odontologoRepository.buscarPorMatricula(matricula);
        if(o == null)
            throw new ResourceNotFoundException("No se encontró el odontologo con matricula: " + matricula);
        return odontologoMapper.toDTO(o);
    }

    @Override
    public OdontologoDto actualizar(OdontologoDto odontologoDto) throws ResourceNotFoundException {
        Odontologo o = odontologoRepository.buscarPorMatricula(odontologoDto.getMatricula());
        if(o == null)
            throw new ResourceNotFoundException("No existe un odontologo con matricula: " + odontologoDto.getMatricula());
        Odontologo odontologoActualizado = odontologoMapper.setEntity(o, odontologoDto);
        Odontologo odontologoGuardado = odontologoRepository.save(odontologoActualizado);
        log.info("El odontologo con matricula: " + odontologoGuardado.getMatricula() + " se ha actualizado de manera exitosa");
        return odontologoMapper.toDTO(odontologoGuardado);
    }

    @Override
    public void eliminar(String matricula) throws ResourceNotFoundException {
        Odontologo o = odontologoRepository.buscarPorMatricula(matricula);
        if(o == null)
            throw new ResourceNotFoundException("No existe un odontologo con matricula: " + matricula);
        odontologoRepository.delete(o);
        log.info("El odontologo con matricula: " + matricula + " se ha eliminado de manera exitosa");
    }


}
