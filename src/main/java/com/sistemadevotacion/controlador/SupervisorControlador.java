package com.sistemadevotacion.controlador;

import com.sistemadevotacion.modelo.*;
import com.sistemadevotacion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/supervisor")
public class SupervisorControlador {

    @Autowired
    private supervisorRepositorio supervisorRepo;

    @Autowired
    private candidatoRepositorio candidatoRepo;

    @Autowired
    private procesoElectoralRepositorio procesoRepo;

    @Autowired
    private VotanteRepositorio votanteRepo;
    
    @Autowired
    private credencialRepositorio credencialRepo;

    
    
    @GetMapping("/gestion")
    public String gestionar0es(Model model) {
        model.addAttribute("usuarios", supervisorRepo.findAll());
        model.addAttribute("usuario", new Supervisor());  
        model.addAttribute("rol", "Supervisor");
        model.addAttribute("accionCrear", "/supervisor/crear");
        model.addAttribute("accionEliminar", "/supervisor/eliminar");
        return "crear";
    }

    @PostMapping("/crear")
    public String crearSupervisor(@ModelAttribute Supervisor supervisor) {
        Credencial credencial = new Credencial();
        credencial.setCorreo(supervisor.getCorreo());
        credencial.setContrasena(supervisor.getContrasena()); 
        credencial.setRol(rol.SUPERVISOR); 

        supervisor.setCredencial(credencial);

        credencial.setSupervisor(supervisor);

        supervisorRepo.save(supervisor);

        return "redirect:/supervisor/gestion";
    }

    @PostMapping("/eliminar")
    public String eliminarSupervisor(@RequestParam Long id) {
        supervisorRepo.deleteById(id);
        return "redirect:/supervisor/gestion";
    }

    @GetMapping("/{id}")
    public String verPanelSupervisor(@PathVariable Long id, Model model) {
        Supervisor supervisor = supervisorRepo.findById(id).orElse(null);
        List<Candidato> candidatosAprobados = candidatoRepo.findByEstado("APROBADO");
        List<ProcesoElectoral> procesos = procesoRepo.findAll();

        model.addAttribute("supervisor", supervisor);
        model.addAttribute("candidatos", candidatosAprobados);
        model.addAttribute("procesos", procesos);
        return "panel_supervisor";
    }
    
    @GetMapping("/procesos")
    public String verProcesosAsignados(Model model, HttpSession session) {
        Long supervisorId = (Long) session.getAttribute("usuarioId");

        Supervisor supervisor = supervisorRepo.findById(supervisorId).orElse(null);
        
        List<ProcesoElectoral> procesos = procesoRepo.findBySupervisor(supervisor);
        
        model.addAttribute("procesos", procesos);
        return "procesos_asignados"; 
    }


   
    
    @GetMapping("/panel")
    public String panelSupervisor(Model model, HttpSession session) {
        Long id = (Long) session.getAttribute("usuarioId");

        Supervisor supervisor = supervisorRepo.findById(id).orElse(null);
        List<Candidato> candidatosAprobados = candidatoRepo.findByEstado("APROBADO");
        List<ProcesoElectoral> procesos = procesoRepo.findAll();

        model.addAttribute("supervisor", supervisor);
        model.addAttribute("candidatos", candidatosAprobados);
        model.addAttribute("procesos", procesos);
        return "panel_supervisor";
    }

}
