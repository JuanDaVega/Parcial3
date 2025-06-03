package com.sistemadevotacion.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemadevotacion.modelo.ProcesoElectoral;
import com.sistemadevotacion.modelo.Credencial;
import com.sistemadevotacion.modelo.Supervisor;
import com.sistemadevotacion.repository.supervisorRepositorio;

import com.sistemadevotacion.repository.procesoElectoralRepositorio;
import com.sistemadevotacion.repository.credencialRepositorio;




@Service
public class procesoService {
	
	@Autowired
	private supervisorRepositorio supervisorRepo;

    @Autowired
    private procesoElectoralRepositorio procesoElectoralRepositorio;

    @Autowired
    private credencialRepositorio credencialRepositorio;

    public void asignarSupervisor(Long procesoId, Long supervisorId) {
        ProcesoElectoral proceso = procesoElectoralRepositorio.findById(procesoId)
            .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));

        Supervisor supervisor = supervisorRepo.findById(supervisorId)
            .orElseThrow(() -> new RuntimeException("Supervisor no encontrado con ID: " + supervisorId));

        proceso.setSupervisor(supervisor);
        procesoElectoralRepositorio.save(proceso);
    }

}