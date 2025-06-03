package com.sistemadevotacion.modelo;

import jakarta.persistence.*;

@Entity
public class Credencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correo;
    private String contrasena;

    @Enumerated(EnumType.STRING)
    private rol rol;

    @OneToOne(mappedBy = "credencial")
    private Administrador administrador;

    @OneToOne(mappedBy = "credencial")
    private Moderador moderador;

    @OneToOne(mappedBy = "credencial")
    private Supervisor supervisor;

    @OneToOne(mappedBy = "credencial")
    private Candidato candidato;

    @OneToOne(mappedBy = "credencial")
    private Votante votante;

    // Getters y setters
    public Administrador getAdministrador() { return administrador; }
    public void setAdministrador(Administrador administrador) { this.administrador = administrador; }

    public Moderador getModerador() { return moderador; }
    public void setModerador(Moderador moderador) { this.moderador = moderador; }

    public Supervisor getSupervisor() { return supervisor; }
    public void setSupervisor(Supervisor supervisor) { this.supervisor = supervisor; }

    public Candidato getCandidato() { return candidato; }
    public void setCandidato(Candidato candidato) { this.candidato = candidato; }

    public Votante getVotante() { return votante; }
    public void setVotante(Votante votante) { this.votante = votante; }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public rol getRol() {
		return rol;
	}
	public void setRol(rol rol) {
		this.rol = rol;
	}


	public Long getUsuarioId() {
	    if (administrador != null) return administrador.getId();
	    if (moderador != null) return moderador.getId();
	    if (supervisor != null) return supervisor.getId();
	    if (candidato != null) return candidato.getId();
	    if (votante != null) return votante.getId();
	    return null;
	}


}


