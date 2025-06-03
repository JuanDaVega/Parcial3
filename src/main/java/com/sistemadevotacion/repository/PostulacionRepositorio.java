package com.sistemadevotacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemadevotacion.modelo.Postulacion;

public interface PostulacionRepositorio extends JpaRepository<Postulacion, Long> {
}
