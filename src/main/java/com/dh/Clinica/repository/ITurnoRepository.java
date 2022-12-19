package com.dh.Clinica.repository;

import com.dh.Clinica.dtos.TurnoDto;
import com.dh.Clinica.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Integer> {

    @Query(value = "SELECT t FROM Turno t JOIN t.paciente p WHERE p.dni = ?1")
    List<Turno> buscarTurnosPorDni(String a);

    @Query(value = "SELECT o.turnos FROM Odontologo o WHERE o.matricula = ?1")
    List<Turno> turnosPorOdontologo(String matricula);

    @Query(value = "SELECT t FROM Turno t WHERE t.id = ?1")
    Turno buscarPorId(int id);









}
