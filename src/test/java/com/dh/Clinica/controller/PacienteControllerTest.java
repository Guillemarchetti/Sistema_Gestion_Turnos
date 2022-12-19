package com.dh.Clinica.controller;

import com.dh.Clinica.dtos.PacienteDto;
import com.dh.Clinica.service.Impl.PacienteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class PacienteControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PacienteServiceImpl pacienteService;



    //------------------------------------Test Crear Paciente -------------------------
    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasanParametrosCorrectos_entonces_creaPacienteYStatus201() throws Exception {
        mockMvc.perform(
                        post("/pacientes/crear")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(pacienteDto("Juan", "Perez", "32102056","Gral. Paz 150", LocalDate.of(2020,01,19)))))
                .andExpect(jsonPath("$.nombre", is("Juan")))
                .andExpect(jsonPath("$.apellido", is("Perez")))
                .andExpect(jsonPath("$.dni", is("32102056")))
                .andExpect(jsonPath("$.domicilio", is("Gral. Paz 150")))
                .andExpect(jsonPath("$.fechaAlta", is("2020-01-19")))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_noSePasanParametros_entonces_retornaStatus400() throws Exception {
        mockMvc.perform(post("/pacientes/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

    }

    //------------------------------------Test Listar Pacientes -------------------------
    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_seLlamaAlMetodoListarPacientes_entonces_devuelveListaYStatus204() throws Exception {
            pacienteService.crear(pacienteDto("Alejandro", "Marchetti", "23450123","Gral. Paz 180", LocalDate.of(2020,01,19)));

            mockMvc.perform(
                        get("/pacientes/listar")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].nombre", is("Juan")))
                .andExpect(jsonPath("$.[1].nombre", is("Alejandro")))
                .andExpect(jsonPath("$.[0].apellido", is("Perez")))
                .andExpect(jsonPath("$.[1].apellido", is("Marchetti")))
                .andExpect(jsonPath("$.[0].dni", is("32102056")))
                .andExpect(jsonPath("$.[1].dni", is("23450123")))
                .andExpect(jsonPath("$.[0].domicilio", is("Gral. Paz 150")))
                .andExpect(jsonPath("$.[1].domicilio", is("Gral. Paz 180")))
                .andExpect(jsonPath("$.[0].fechaAlta", is("2020-01-19")))
                .andExpect(jsonPath("$.[1].fechaAlta", is("2020-01-19")))
                .andExpect(status().isOk());
    }

    //------------------------------------Test Buscar por dni -------------------------

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasaDniExistentePorParametro_entonces_devuelvePacienteYStatus204() throws Exception {
        pacienteService.crear(pacienteDto("Analia", "Gonzalez", "25435678","España 2134", LocalDate.of(2020,02,19)));

        mockMvc.perform(
                        get("/pacientes/buscar/25435678")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre", is("Analia")))
                .andExpect(jsonPath("$.apellido", is("Gonzalez")))
                .andExpect(jsonPath("$.dni", is("25435678")))
                .andExpect(jsonPath("$.domicilio", is("España 2134")))
                .andExpect(jsonPath("$.fechaAlta", is("2020-02-19")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasaDniInexistentePorParametro_entonces_devuelveStatus404() throws Exception {
        mockMvc.perform(
                   get("/pacientes/buscar/23450124")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string("No se encontró el paciente con DNI: 23450124"))
                        .andExpect(status().isNotFound());
    }


    //------------------------------------Test Eliminar Paciente -------------------------

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasaElDniExistente_entonces_devuelveStatus204() throws Exception {
        pacienteService.crear(pacienteDto("Diego", "Perez", "10948967","Gral. Paz 150", LocalDate.of(2020,01,19)));
        mockMvc.perform(
                        delete("/pacientes/eliminar/10948967")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasaDniInexistente_entonces_devuelveStatus404() throws Exception {
        mockMvc.perform(
                        delete("/pacientes/eliminar/23450124")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("No existe un paciente con DNI: 23450124"))
                .andExpect(status().isNotFound());
    }












    public PacienteDto pacienteDto (String nombre, String apellido, String dni, String domicilio, LocalDate fechaAlta){
        PacienteDto pDto = new PacienteDto();
        pDto.setNombre(nombre);
        pDto.setApellido(apellido);
        pDto.setDni(dni);
        pDto.setDomicilio(domicilio);
        pDto.setFechaAlta(fechaAlta);
        return pDto;
    }


}