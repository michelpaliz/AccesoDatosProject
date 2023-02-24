package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.Mensajeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeriaRepository extends JpaRepository<Mensajeria,Long> {
}
