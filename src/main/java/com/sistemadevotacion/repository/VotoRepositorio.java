package com.sistemadevotacion.repository;

import com.sistemadevotacion.modelo.Voto;
import com.sistemadevotacion.modelo.ProcesoElectoral;
import com.sistemadevotacion.modelo.Votante;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface VotoRepositorio extends JpaRepository<Voto, Long> {
    boolean existsByVotante(Votante votante);

    Optional<Voto> findByVotante(Votante votante);
}
