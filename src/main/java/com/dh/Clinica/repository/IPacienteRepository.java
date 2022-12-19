package com.dh.Clinica.repository;


import com.dh.Clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente,Integer> {

    @Query(value = "SELECT p FROM Paciente p WHERE p.dni = ?1" )
    Paciente buscarPorDni(String dni);



}
