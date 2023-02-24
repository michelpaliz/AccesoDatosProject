package com.gestion.empleados.servicio;
import com.gestion.empleados.entidades.Departamento;
import com.gestion.empleados.repositorios.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class DepartamentoServiceImpl implements DepartamentoService{

    @Autowired
    private DepartamentoRepository departamentoRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Departamento> findAll() {
        return departamentoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Departamento> findAll(Pageable pageable) {
        return departamentoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Departamento departamento) {
        departamentoRepository.save(departamento);
    }

    @Override
    @Transactional
    public Departamento findOne(Long id) {
        return departamentoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        departamentoRepository.deleteById(id);
    }
}
