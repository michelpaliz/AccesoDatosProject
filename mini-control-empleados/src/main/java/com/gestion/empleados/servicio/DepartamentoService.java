package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Departamento;
import com.gestion.empleados.entidades.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface DepartamentoService {

    public List<Departamento> findAll();

    public Page<Departamento> findAll(Pageable pageable);

    public void save(Departamento departamento);

    public Departamento findOne(Long id);

    public void delete(Long id);



}
