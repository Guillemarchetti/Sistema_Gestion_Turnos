package com.dh.Clinica.repository;

import com.dh.Clinica.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo,Integer> {

    @Query(value = "SELECT o FROM Odontologo o WHERE o.matricula = ?1" )
    public Odontologo buscarPorMatricula(String matricula);



}
