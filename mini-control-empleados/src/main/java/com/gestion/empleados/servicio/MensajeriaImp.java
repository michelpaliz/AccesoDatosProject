package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.Mensajeria;
import com.gestion.empleados.repositorios.MensajeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MensajeriaImp implements MensajeService {

    @Autowired
    private MensajeriaRepository mensajeriaRepository;

    @Override
    public void save(Mensajeria mensajeria) {
        mensajeriaRepository.save(mensajeria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mensajeria> findAll() {
        return  mensajeriaRepository.findAll();
    }


}
