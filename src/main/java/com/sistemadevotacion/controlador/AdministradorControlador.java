package com.sistemadevotacion.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

import com.sistemadevotacion.modelo.Supervisor;
import com.sistemadevotacion.repository.supervisorRepositorio;

import com.sistemadevotacion.modelo.Cargo;
import com.sistemadevotacion.modelo.Credencial;
import com.sistemadevotacion.modelo.ProcesoElectoral;
import com.sistemadevotacion.modelo.rol;

import com.sistemadevotacion.repository.cargoRepositorio;
import com.sistemadevotacion.repository.credencialRepositorio;
import com.sistemadevotacion.repository.procesoElectoralRepositorio;

import com.sistemadevotacion.servicio.procesoService;



@Controller
@RequestMapping("/admin")
public class AdministradorControlador {

    @Autowired
    private procesoElectoralRepositorio procesoRepo;

    @Autowired
    private cargoRepositorio cargoRepo;
    
    
    @Autowired
    private credencialRepositorio credencialRepo;


    @Autowired
	private procesoService procesoService;
    
    @Autowired
    private supervisorRepositorio supervisorRepo;

    
    @GetMapping("/crear")
    public String formularioProceso(Model model) {
        model.addAttribute("proceso", new ProcesoElectoral());
        model.addAttribute("procesos", procesoRepo.findAll()); 
        return "crear_proceso";
    }

    
    @GetMapping("/editar/{id}")
    public String editarProceso(@PathVariable Long id, Model model) {
        ProcesoElectoral proceso = procesoRepo.findById(id).orElse(null);
        if (proceso != null) {
            model.addAttribute("proceso", proceso);
            return "editar_proceso";
        }
        return "redirect:/admin/crear";
    }
    @PostMapping("/actualizar")
    public String actualizarProceso(@ModelAttribute ProcesoElectoral proceso) {
        procesoRepo.save(proceso);
        return "redirect:/admin/crear";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProceso(@PathVariable Long id) {
        procesoRepo.deleteById(id);
        return "redirect:/admin/crear";
    }
    @GetMapping("/asignarSupervisor")
    public String mostrarFormularioAsignacion(Model model) {
        model.addAttribute("procesos", procesoRepo.findAll());
        model.addAttribute("supervisores", supervisorRepo.findAll());
        return "asignar_supervisor";
    }

    @PostMapping("/asignarSupervisor")
    public String asignarSupervisorPost(@RequestParam Long procesoId, @RequestParam Long supervisorId, Model model) {
        procesoService.asignarSupervisor(procesoId, supervisorId);
        model.addAttribute("mensaje", "Supervisor asignado correctamente al proceso ID " + procesoId);
        return "asignacion_exitosa";
    }


    @PostMapping("/guardar")
    public String guardarProceso(@ModelAttribute ProcesoElectoral proceso) {
        procesoRepo.save(proceso);
        return "redirect:/admin/crear";
    }

    @GetMapping("/listar")
    public String listarProcesos(Model model) {
        model.addAttribute("procesos", procesoRepo.findAll());
        return "listar_procesos";
    }

    @GetMapping("/agregarCargo/{id}")
    public String formularioCargo(@PathVariable Long id, Model model) {
        model.addAttribute("cargo", new Cargo());
        model.addAttribute("procesoId", id);
        return "crear_cargo";
    }

    @PostMapping("/guardarCargo")
    public String guardarCargo(@ModelAttribute Cargo cargo, @RequestParam Long procesoId) {
        ProcesoElectoral proceso = procesoRepo.findById(procesoId).orElse(null);
        if (proceso != null) {
            cargo.setProcesoElectoral(proceso);
            cargoRepo.save(cargo);
        }
        return "redirect:/admin/listar";
    }
    
    @GetMapping("/usuarios/crear")
    public String mostrarFormularioUsuario(Model model) {
        model.addAttribute("credencial", new Credencial());
        return "crear_usuario"; 
    }

    
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute Credencial credencial) {
        credencialRepo.save(credencial);
        return "redirect:/admin/usuarios";
    }

    
    
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", credencialRepo.findAll());
        return "usuarios";
    }

    
    
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Credencial usuario = credencialRepo.findById(id).orElse(null);
        if (usuario != null) {
            model.addAttribute("credencial", usuario);
            return "editar_usuario";
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(@ModelAttribute Credencial credencial) {
        credencialRepo.save(credencial);
        return "redirect:/admin/usuarios";
    }

    
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        credencialRepo.deleteById(id);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/panel")
    public String panelAdministrador(Model model, HttpSession session) {
        Long id = (Long) session.getAttribute("usuarioId");
        return "panel_administrador";
    }
    
    
    
    
    
    
    
    
}
