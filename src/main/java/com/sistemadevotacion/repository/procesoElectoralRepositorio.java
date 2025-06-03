package com.sistemadevotacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemadevotacion.modelo.ProcesoElectoral;
import com.sistemadevotacion.modelo.Supervisor;
import com.sistemadevotacion.modelo.Votante;

public interface procesoElectoralRepositorio extends JpaRepository<ProcesoElectoral, Long> {

	List<ProcesoElectoral> findBySupervisor(Supervisor supervisor);



}
