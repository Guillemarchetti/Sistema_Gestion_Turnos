package com.dh.Clinica.controller;


import com.dh.Clinica.dtos.OdontologoDto;
import com.dh.Clinica.service.Impl.OdontologoServiceImpl;
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


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class) //Ejecuta el test dentro del contexto de Spring (JUnit5)
@AutoConfigureMockMvc
class OdontologoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OdontologoServiceImpl odontologoService;



    //------------------------------------Test Crear Odontologo -------------------------
    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasanParametrosCorrectos_entonces_creaOdontologoYStatus201() throws Exception {
        mockMvc.perform(
                        post("/odontologos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(odontologoDto("Juan", "Perez", "2345"))))
                        .andExpect(jsonPath("$.nombre", is("Juan")))
                        .andExpect(jsonPath("$.apellido", is("Perez")))
                        .andExpect(jsonPath("$.matricula", is("2345")))
                        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_noSePasanParametros_entonces_devuelveStatus400() throws Exception {
        mockMvc.perform(
                  post("/odontologos/crear")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(null)))
                 .andExpect(status().isBadRequest());

    }

    //------------------------------------Test Listar Odontologos -------------------------
    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_seLlamaAlMetodoListarOdontologos_entonces_devuelveListaYStatus200() throws Exception {
        odontologoService.crear(odontologoDto("Alejandro", "Marchetti", "1234"));

        mockMvc.perform(
                        get("/odontologos/listar")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].nombre", is("Juan")))
                .andExpect(jsonPath("$.[1].nombre", is("Alejandro")))
                .andExpect(jsonPath("$.[0].apellido", is("Perez")))
                .andExpect(jsonPath("$.[1].apellido", is("Marchetti")))
                .andExpect(jsonPath("$.[0].matricula", is("2345")))
                .andExpect(jsonPath("$.[1].matricula", is("1234")))
                .andExpect(status().isOk());
    }

    //------------------------------------Test Buscar por matricula -------------------------

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasaMatriculaExistentePorParametro_entonces_devuelveOdontologoYStatus200() throws Exception {
        mockMvc.perform(
                        get("/odontologos/buscar/2345")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre", is("Juan")))
                .andExpect(jsonPath("$.apellido", is("Perez")))
                .andExpect(jsonPath("$.matricula", is("2345")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasaMatriculaInexistentePorParametro_entonces_devuelveStatus404() throws Exception {
        mockMvc.perform(
                        get("/odontologos/buscar/2348")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("No se encontró el odontologo con matricula: 2348"))
                .andExpect(status().isNotFound());
    }


    //------------------------------------Test Eliminar Odontologo -------------------------

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasaMatriculaCorrecta_entonces_devuelveStatus204() throws Exception {
        odontologoService.crear(odontologoDto("Analia", "Gonzalez", "3467"));
        mockMvc.perform(
                        delete("/odontologos/eliminar/3467")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username="admin@gmail.com", password = "admin")
    void cuando_sePasaMatriculaIncorrecta_entonces_devuelveStatus404() throws Exception {
        mockMvc.perform(
                        delete("/odontologos/eliminar/2348")
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().string("No existe un odontologo con matricula: 2348"))
                    .andExpect(status().isNotFound());
    }


















    public OdontologoDto odontologoDto (String nombre, String apellido, String matricula){
        OdontologoDto oDto = new OdontologoDto();
        oDto.setNombre(nombre);
        oDto.setApellido(apellido);
        oDto.setMatricula(matricula);
        return oDto;
    }

}