package com.dh.Clinica.service;

import com.dh.Clinica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IService <T>{

    public T crear(T t);
    public List<T> listar();
    public T buscar(String a) throws Exception;
    public T actualizar(T t) throws Exception;
    public void eliminar(String a) throws Exception;
}
