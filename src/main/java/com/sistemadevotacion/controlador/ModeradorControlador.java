package com.sistemadevotacion.controlador;

import com.sistemadevotacion.modelo.Candidato;
import com.sistemadevotacion.modelo.Credencial;
import com.sistemadevotacion.repository.ModeradorRepositorio;
import com.sistemadevotacion.modelo.Moderador;
import com.sistemadevotacion.modelo.Supervisor;
import com.sistemadevotacion.modelo.Votante;
import com.sistemadevotacion.modelo.rol;
import com.sistemadevotacion.repository.candidatoRepositorio;
import com.sistemadevotacion.repository.credencialRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/moderador")
public class ModeradorControlador {

    @Autowired
    private candidatoRepositorio candidatoRepo;
    @Autowired
    private ModeradorRepositorio ModeradorRepositorio;

    @Autowired
    private credencialRepositorio credencialRepo;

    

    @GetMapping("/pendientes")
    public String listarPendientes(Model modelo) {
        modelo.addAttribute("pendientes", candidatoRepo.findByEstado("pendiente"));
        return "moderador/listarCandidatosPendientes";
    }
    
    
    @GetMapping("/gestion")
    public String gestionarModeradores(Model model) {
        model.addAttribute("usuarios", ModeradorRepositorio.findAll());
        model.addAttribute("usuario", new Moderador());  
        model.addAttribute("rol", "Moderador");
        model.addAttribute("accionCrear", "/moderador/crear");
        model.addAttribute("accionEliminar", "/moderador/eliminar");
        return "crear";
    }
    
    
    @PostMapping("/crear")
    public String crearModerador(@ModelAttribute Moderador moderador) {
        Credencial credencial = new Credencial();
        credencial.setCorreo(moderador.getCorreo());
        credencial.setContrasena(moderador.getContrasena()); 
        credencial.setRol(rol.MODERADOR); 

        moderador.setCredencial(credencial);

        credencial.setModerador(moderador);

        ModeradorRepositorio.save(moderador);

        return "redirect:/moderador/gestion";
    }


    @PostMapping("/eliminar")
    public String eliminarModerador(@RequestParam Long id) {
        ModeradorRepositorio.deleteById(id);
        return "redirect:/moderador/usuarios";
    }

    
    
    

    @GetMapping("/aprobar/{id}")
    public String aprobarCandidato(@PathVariable Long id) {
        Candidato candidato = candidatoRepo.findById(id).orElse(null);
        if (candidato != null) {
            candidato.setEstado("aprobado");
            candidatoRepo.save(candidato);
        }
        return "redirect:/moderador/pendientes";
    }

    @GetMapping("/rechazar/{id}")
    public String rechazarCandidato(@PathVariable Long id) {
        Candidato candidato = candidatoRepo.findById(id).orElse(null);
        if (candidato != null) {
            candidato.setEstado("rechazado");
            candidatoRepo.save(candidato);
        }
        return "redirect:/moderador/pendientes";
    }
    
    @GetMapping("/panel")
    public String panelModerador(Model model, HttpSession session) {
        Long id = (Long) session.getAttribute("usuarioId");
        return "panel_moderador";
    }

}
