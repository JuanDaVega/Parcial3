package com.sistemadevotacion.repository;

import com.sistemadevotacion.modelo.Credencial;
import com.sistemadevotacion.modelo.rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface credencialRepositorio extends JpaRepository<Credencial, Long> {

    Optional<Credencial> findByCorreo(String correo);
    
    
    @Query("SELECT c FROM Credencial c WHERE c.rol = 'SUPERVISOR' AND c.id NOT IN (SELECT p.supervisor.id FROM ProcesoElectoral p WHERE p.supervisor IS NOT NULL)")
    List<Credencial> findSupervisoresDisponibles();

    List<Credencial> findByRol(rol rol);
    
}
