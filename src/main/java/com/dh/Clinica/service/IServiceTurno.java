package com.dh.Clinica.service;

import com.dh.Clinica.dtos.TurnoDto;
import com.dh.Clinica.exceptions.BadRequestException;
import com.dh.Clinica.exceptions.ResourceNotFoundException;
import com.dh.Clinica.model.Turno;

import java.util.List;

public interface IServiceTurno{


    TurnoDto crear(TurnoDto turnoDto) throws BadRequestException, ResourceNotFoundException;

    List<TurnoDto> listar();

    List<TurnoDto> buscar(String dni) throws ResourceNotFoundException;

    TurnoDto actualizar(int id,TurnoDto tDto) throws ResourceNotFoundException;

    void eliminar(int id) throws ResourceNotFoundException;
}
