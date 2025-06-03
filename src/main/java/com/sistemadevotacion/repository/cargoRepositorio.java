package com.sistemadevotacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemadevotacion.modelo.Cargo;

public interface cargoRepositorio extends JpaRepository<Cargo, Long> {
}
