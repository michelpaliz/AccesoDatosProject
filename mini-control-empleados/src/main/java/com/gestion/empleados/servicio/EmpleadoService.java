package com.gestion.empleados.servicio;

import java.util.List;

import com.gestion.empleados.entidades.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpleadoService {

	public List<Empleado> findAll();

	public List<Empleado> filter(String nombre);

	public Page<Empleado> findAll(Pageable pageable);

	public void save(Empleado empleado);

	public Empleado findOne(Long id);

	public Empleado findByName(String name);

	public void delete(Long id);
}
