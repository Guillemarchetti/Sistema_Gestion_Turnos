package com.dh.Clinica.service.Impl;

import com.dh.Clinica.dtos.OdontologoDto;
import com.dh.Clinica.exceptions.GlobalException;
import com.dh.Clinica.exceptions.ResourceNotFoundException;
import com.dh.Clinica.model.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class OdontologoServiceImplTest {

    @Autowired
    private OdontologoServiceImpl serviceTest;

    @Autowired
    private GlobalException exception;

    @Test
    void seObtieneUnNuevoRegistroDeOdontologo() {

        OdontologoDto o = new OdontologoDto("Marchetti", "Guillermina","5674");

        OdontologoDto resultado = serviceTest.crear(o);

        Assertions.assertEquals("5674", resultado.getMatricula());
    }



    @Test
    void SeObtieneLalistaDeOdontologos() {

        List<OdontologoDto> odontologos =  serviceTest.listar();

        assertTrue(odontologos.size() > 0);
    }


    @Test
    void BuscarOdontologoPorMatricula() throws ResourceNotFoundException {

        serviceTest.crear(new OdontologoDto("Lopez", "Juan", "3212"));

        OdontologoDto o = serviceTest.buscar("3212");

        Assertions.assertEquals("3212", o.getMatricula());
    }


    @Test
    void SeActualizaElRegistroSeleccionadoPorMatricula() throws ResourceNotFoundException {
        OdontologoDto oDto1 = new OdontologoDto("Gonzalez", "Ana", "3456");
        serviceTest.crear(oDto1);
        OdontologoDto oDto2 = new OdontologoDto("Gonzalez", "Analía", "3456");

        serviceTest.actualizar(oDto2);

        OdontologoDto oModificado = serviceTest.buscar("3456");

        Assertions.assertEquals("Analía", oModificado.getNombre());
    }



    @Test
    void SeEliminaElRegistroCorrespondientePorMatricula() throws ResourceNotFoundException {
        OdontologoDto oDto = new OdontologoDto("Gonzalez", "Ana", "1654");
        serviceTest.crear(oDto);
        serviceTest.eliminar("1654");


        Assertions.assertThrows(ResourceNotFoundException.class, () -> serviceTest.buscar("1654"));
    }



}