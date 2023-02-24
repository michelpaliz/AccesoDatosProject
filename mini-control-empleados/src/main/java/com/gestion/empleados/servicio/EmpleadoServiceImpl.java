package com.gestion.empleados.servicio;

import java.util.List;

import com.gestion.empleados.entidades.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.repositorios.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		return (List<Empleado>) empleadoRepository.findAll();
	}

	@Override
	public List<Empleado> filter(String nombre) {
		return empleadoRepository.filter(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Empleado> findAll(Pageable pageable) {
		return empleadoRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Empleado empleado) {
		empleadoRepository.save(empleado);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		empleadoRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Empleado findOne(Long id) {
		return empleadoRepository.findById(id).orElse(null);
	}

	@Override
	public Empleado findByName(String name) {
		List<Empleado> empleados = (List<Empleado>) empleadoRepository.findAll();
		Empleado empleadoR = null;
		for (Empleado empleado: empleados) {
			if (empleado.getNombre().equalsIgnoreCase(name)){
				empleadoR = empleado;
			}
		}
		assert empleadoR != null;
		System.out.println("Esto es el usuario " + empleadoR);

		return  empleadoR;
	}

}
