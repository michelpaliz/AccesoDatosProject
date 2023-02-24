package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Mensajeria;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MensajeService  {

    public void save(Mensajeria mensajeria);
    public List<Mensajeria> findAll();

}
