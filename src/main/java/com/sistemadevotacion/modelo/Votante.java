package com.sistemadevotacion.modelo;

import jakarta.persistence.*;

@Entity
public class Votante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    private int docIdentidad; 


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credencial_id", referencedColumnName = "id")
    private Credencial credencial;

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


	  public int getDocIdentidad() {
	        return docIdentidad;
	    }

	    public void setDocIdentidad(int docIdentidad) {
	        this.docIdentidad = docIdentidad;
	    }


	public Credencial getCredencial() {
		return credencial;
	}


	public void setCredencial(Credencial credencial) {
		this.credencial = credencial;
	}
    
	public ProcesoElectoral getProcesoElectoral() {
	    return procesoElectoral;
	}

	public void setProcesoElectoral(ProcesoElectoral procesoElectoral) {
	    this.procesoElectoral = procesoElectoral;
	}

}
