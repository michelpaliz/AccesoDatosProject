package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository  extends JpaRepository<Departamento, Long> {
}
