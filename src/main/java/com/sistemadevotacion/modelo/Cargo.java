package com.sistemadevotacion.modelo;

import jakarta.persistence.*;

@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "proceso_electoral_id")
    private ProcesoElectoral procesoElectoral;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ProcesoElectoral getProcesoElectoral() {
		return procesoElectoral;
	}

	public void setProcesoElectoral(ProcesoElectoral procesoElectoral) {
		this.procesoElectoral = procesoElectoral;
	}


}
