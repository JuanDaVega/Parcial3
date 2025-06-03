package com.sistemadevotacion.repository;

import com.sistemadevotacion.modelo.Supervisor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface supervisorRepositorio extends JpaRepository<Supervisor, Long> {

	Optional<Supervisor> findByCredencialId(Long credencialId);

}
