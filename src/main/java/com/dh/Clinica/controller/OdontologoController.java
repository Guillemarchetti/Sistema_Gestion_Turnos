package com.dh.Clinica.controller;

import com.dh.Clinica.exceptions.ResourceNotFoundException;
import com.dh.Clinica.dtos.OdontologoDto;
import com.dh.Clinica.service.Impl.OdontologoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoServiceImpl odontologoService;


    @PostMapping("/crear")
    public ResponseEntity<OdontologoDto> crearOdontologo(@Valid @RequestBody OdontologoDto oDto){
        return new ResponseEntity<>(odontologoService.crear(oDto), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OdontologoDto>> listarOdontologos () {
        return new ResponseEntity<>(odontologoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/buscar/{matricula}")
    public ResponseEntity<OdontologoDto> buscarOdontologo(@PathVariable String matricula) throws ResourceNotFoundException {
        return new ResponseEntity<>(odontologoService.buscar(matricula), HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<OdontologoDto> actualizarOdontologo(@RequestBody OdontologoDto oDto) throws ResourceNotFoundException {
         return new ResponseEntity<>(odontologoService.actualizar(oDto),HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{matricula}")
    public ResponseEntity<HttpStatus> eliminarOdontologo(@PathVariable String matricula) throws ResourceNotFoundException {
        odontologoService.eliminar(matricula);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
