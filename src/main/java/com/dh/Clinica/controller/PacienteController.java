package com.dh.Clinica.controller;

import com.dh.Clinica.exceptions.ResourceNotFoundException;
import com.dh.Clinica.dtos.PacienteDto;
import com.dh.Clinica.service.Impl.PacienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteServiceImpl pacienteService;


    @PostMapping("/crear")
    public ResponseEntity<PacienteDto> crearPaciente(@Valid @RequestBody  PacienteDto pDto){
        return new ResponseEntity<>(pacienteService.crear(pDto), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteDto>> listarPacientes () {
        return new ResponseEntity<>(pacienteService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar/{dni}")
    public ResponseEntity<PacienteDto> buscarPaciente(@PathVariable String dni) throws ResourceNotFoundException {
        return new ResponseEntity<>(pacienteService.buscar(dni), HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<PacienteDto> actualizarPaciente(@RequestBody PacienteDto pDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(pacienteService.actualizar(pDto),HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{dni}")
    public ResponseEntity<HttpStatus> eliminarPaciente(@PathVariable String dni) throws ResourceNotFoundException {
        pacienteService.eliminar(dni);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
