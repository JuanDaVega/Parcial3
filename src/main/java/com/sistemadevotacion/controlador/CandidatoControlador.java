package com.sistemadevotacion.controlador;

import com.sistemadevotacion.modelo.*;
import com.sistemadevotacion.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/candidato")
public class CandidatoControlador {

    @Autowired
    private PostulacionRepositorio postulacionRepo;

    @Autowired
    private cargoRepositorio cargoRepo;

    @Autowired
    private candidatoRepositorio candidatoRepo;

    @GetMapping("/gestion")
    public String gestionarSupervisores(Model model) {
        model.addAttribute("usuarios", candidatoRepo.findAll());
        model.addAttribute("usuario", new Candidato());  
        model.addAttribute("rol", "Candidato");
        model.addAttribute("accionCrear", "/candidato/crear");
        model.addAttribute("accionEliminar", "/candidato/eliminar");
        return "crear_candidato";
    }
    
    
    
    @PostMapping("/crear")
    public String crearCandidato(@ModelAttribute Candidato candidato) {
        Credencial credencial = new Credencial();
        credencial.setCorreo(candidato.getCorreo());
        credencial.setContrasena(candidato.getContrasena()); 
        credencial.setRol(rol.CANDIDATO); 

        candidato.setCredencial(credencial);

        credencial.setCandidato(candidato);

        candidatoRepo.save(candidato);

        return "redirect:/candidato/gestion";
    }

    @PostMapping("/eliminar")
    public String eliminarCandidato(@RequestParam Long id) {
        candidatoRepo.deleteById(id);
        return "redirect:/candidato/gestion";
    }


    // ----------------- FORMULARIO DE POSTULACIÓN ------------------
    @GetMapping("/postular")
    public String mostrarFormularioPostulacion(Model model) {
        model.addAttribute("postulacion", new Postulacion());
        model.addAttribute("cargos", cargoRepo.findAll());
        return "form_postulacion";
    }

    @PostMapping("/guardar")
    public String guardarPostulacion(@ModelAttribute Postulacion postulacion) {
        postulacion.setEstado("Pendiente");
        postulacionRepo.save(postulacion);
        return "redirect:/candidato/mispostulaciones";
    }

    @GetMapping("/mispostulaciones")
    public String listarMisPostulaciones(Model model) {
        List<Postulacion> lista = postulacionRepo.findAll(); // Puedes filtrar por usuario
        model.addAttribute("postulaciones", lista);
        return "listar_postulaciones";
    }

    @GetMapping("/subirPropuesta/{id}")
    public String subirPropuesta(@PathVariable Long id, Model model) {
        Postulacion postulacion = postulacionRepo.findById(id).orElse(null);
        if (postulacion != null && "Aprobado".equals(postulacion.getEstado())) {
            model.addAttribute("postulacion", postulacion);
            return "subir_propuesta";
        }
        return "redirect:/candidato/mispostulaciones";
    }

    @PostMapping("/guardarPropuesta")
    public String guardarPropuesta(@RequestParam Long id, @RequestParam String propuesta) {
        Postulacion postulacion = postulacionRepo.findById(id).orElse(null);
        if (postulacion != null) {
            postulacion.setPropuesta(propuesta);
            postulacionRepo.save(postulacion);
        }
        return "redirect:/candidato/mispostulaciones";
    }

    @GetMapping("/panel")
    public String panelCandidato(Model model, HttpSession session) {
        Long id = (Long) session.getAttribute("usuarioId");
        return "panel_candidato";
    }
}
