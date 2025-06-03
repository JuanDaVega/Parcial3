package com.sistemadevotacion.modelo;

import jakarta.persistence.*;

@Entity
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String contrasena;

    private String estado = "PENDIENTE"; 

    @ManyToOne
    private Moderador moderador;
    
    @ManyToOne
    @JoinColumn(name = "proceso_electoral_id", nullable = false)
    private ProcesoElectoral procesoElectoral;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credencial_id", referencedColumnName = "id")
    private Credencial credencial;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Moderador getModerador() {
        return moderador;
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }

    public Credencial getCredencial() {
        return credencial;
    }

    public void setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }
}
