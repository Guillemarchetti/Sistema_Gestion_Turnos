package com.dh.Clinica.service.Impl;

import com.dh.Clinica.dtos.PacienteDto;
import com.dh.Clinica.exceptions.GlobalException;
import com.dh.Clinica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PacienteServiceImplTest {

    @Autowired
    private PacienteServiceImpl serviceTest;


    @Test
    void seObtieneUnNuevoRegistroDePaciente() {

        PacienteDto o = new PacienteDto("Marchetti", "Guillermina","10948967","Paz 150", LocalDate.of(2020,01,19) );

        PacienteDto resultado = serviceTest.crear(o);

        Assertions.assertEquals("10948967", resultado.getDni());
    }



    @Test
    void SeObtieneLalistaDePacientes() {

        List<PacienteDto> pacientes =  serviceTest.listar();

        assertTrue(pacientes.size() > 0);
    }


    @Test
    void BuscarPacientePorDni() throws ResourceNotFoundException {

        serviceTest.crear(new PacienteDto("Lopez", "Juan", "32120058", "España 1234", LocalDate.of(2020,01,19)));

        PacienteDto o = serviceTest.buscar("32120058");

        Assertions.assertEquals("32120058", o.getDni());
    }


    @Test
    void SeActualizaElRegistroSeleccionadoPorDni() throws ResourceNotFoundException {
        PacienteDto oDto1 = new PacienteDto("Gonzalez", "Ana", "34560123", "España 1234",LocalDate.of(2020,01,19) );
        serviceTest.crear(oDto1);
        PacienteDto oDto2 = new PacienteDto("Gonzalez", "Analía", "34560123","España 1234",LocalDate.of(2020,01,19) );

        serviceTest.actualizar(oDto2);

        PacienteDto oModificado = serviceTest.buscar("34560123");

        Assertions.assertEquals("Analía", oModificado.getNombre());
    }



    @Test
    void SeEliminaElRegistroCorrespondientePorDni() throws ResourceNotFoundException {
        PacienteDto oDto = new PacienteDto("Gonzalez", "Maria", "16540123","España 1234",LocalDate.of(2020,01,19) );
        serviceTest.crear(oDto);
        serviceTest.eliminar("16540123");


        Assertions.assertThrows(ResourceNotFoundException.class, () -> serviceTest.buscar("1654"));
    }

}