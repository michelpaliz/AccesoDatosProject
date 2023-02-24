package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.Departamento;
import com.gestion.empleados.entidades.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, Long>{

    @Query("select e from Empleado e where e.nombre like %?1%" +
            "or  e.email like %?1% ")
    public List<Empleado> filter(String keyword);

}
