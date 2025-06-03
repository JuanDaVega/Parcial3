package com.sistemadevotacion.repository;

import com.sistemadevotacion.modelo.Votante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VotanteRepositorio extends JpaRepository<Votante, Long> {
    Votante findByDocIdentidad(int docIdentidad); 


}
