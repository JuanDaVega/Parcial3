package com.sistemadevotacion.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class ProcesoElectoral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "procesoElectoral", cascade = CascadeType.ALL)
    private List<Cargo> cargos;

    @ManyToOne
    private Supervisor supervisor;

    @OneToOne    
    @JoinColumn(name = "votante_id")

    private Votante votante;

    
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}
	public Supervisor getSupervisor() {
	    return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
	    this.supervisor = supervisor;
	}



}
