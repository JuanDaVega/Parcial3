package com.sistemadevotacion.repository;

import com.sistemadevotacion.modelo.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemadevotacion.modelo.ProcesoElectoral;

import java.util.List;

public interface candidatoRepositorio extends JpaRepository<Candidato, Long> {

    List<Candidato> findByEstado(String estado);

     List<Candidato> findByCredencialCorreo(String correo);
     List<Candidato> findByProcesoElectoral(ProcesoElectoral proceso);

}
