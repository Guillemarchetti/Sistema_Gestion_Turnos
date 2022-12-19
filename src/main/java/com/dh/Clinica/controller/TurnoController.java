package com.dh.Clinica.controller;

import com.dh.Clinica.dtos.TurnoDto;
import com.dh.Clinica.exceptions.BadRequestException;
import com.dh.Clinica.exceptions.ResourceNotFoundException;
import com.dh.Clinica.service.Impl.TurnoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoServiceImpl turnoService;


    @PostMapping("/crear")
    public ResponseEntity<TurnoDto> crearTurno(@RequestBody TurnoDto oDto) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.crear(oDto), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurnoDto>> listarTurnos () {
        return new ResponseEntity<>(turnoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar/{dni}")
    public ResponseEntity<List<TurnoDto>> buscarTurnoPorDni(@PathVariable String dni) throws ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.buscar(dni), HttpStatus.OK);
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<TurnoDto> actualizarTurno(@RequestBody TurnoDto oDto, @PathVariable int id) throws ResourceNotFoundException {
         return new ResponseEntity<>(turnoService.actualizar(id, oDto),HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<HttpStatus> eliminarTurno(@PathVariable int id) throws ResourceNotFoundException {
        turnoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
