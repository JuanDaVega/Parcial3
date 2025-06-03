package com.sistemadevotacion.modelo;

import jakarta.persistence.*;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Votante votante;

    @ManyToOne
    private Candidato candidato;

    @ManyToOne
    private ProcesoElectoral procesoElectoral;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Votante getVotante() {
        return votante;
    }

    public void setVotante(Votante votante) {
        this.votante = votante;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

	public ProcesoElectoral getProcesoElectoral() {
		return procesoElectoral;
	}

	public void setProcesoElectoral(ProcesoElectoral procesoElectoral) {
		this.procesoElectoral = procesoElectoral;
	}
    

    
}
