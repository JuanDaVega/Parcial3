package com.sistemadevotacion.controlador;

import com.sistemadevotacion.modelo.*;
import com.sistemadevotacion.repository.*;
import com.sistemadevotacion.repository.VotanteRepositorio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/votante")
public class VotanteControlador {

    @Autowired
    private procesoElectoralRepositorio procesoRepo;

    @Autowired
    private cargoRepositorio cargoRepo;

    @Autowired
    private candidatoRepositorio candidatoRepo;

    @Autowired
    private VotoRepositorio votoRepo;

    @Autowired
    private VotanteRepositorio votanteRepo;
    
    @GetMapping("/gestion")
    public String gestionarVotantes(Model model) {
        model.addAttribute("usuarios", votanteRepo.findAll());
        model.addAttribute("usuario", new Votante());  
        model.addAttribute("rol", "Votante");
        model.addAttribute("accionCrear", "/votante/crear");
        model.addAttribute("accionEliminar", "/votante/eliminar");
        return "crear_usuario";
    }

   

    @PostMapping("/crear")
    public String crearVotante(@RequestParam String nombre,
                                @RequestParam int docIdentidad) {

        String docComoContrasena = String.valueOf(docIdentidad);

        Credencial credencial = new Credencial();
        credencial.setCorreo(nombre + "@votante"); 
        credencial.setContrasena(docComoContrasena);   
        credencial.setRol(rol.VOTANTE);

        Votante votante = new Votante();
        votante.setNombre(nombre);
        votante.setDocIdentidad(docIdentidad);
        votante.setCredencial(credencial);

        credencial.setVotante(votante);

        votanteRepo.save(votante);

        return "redirect:/votante/gestion"; 
    }

    @PostMapping("/eliminar")
    public String eliminarVotante(@RequestParam Long id) {
        votanteRepo.deleteById(id);
        return "redirect:/votante/gestion";
    }
    

    @GetMapping("/procesos")
    public String verProcesos(Model model) {
        model.addAttribute("procesos", procesoRepo.findAll());
        return "votante_procesos";
    }

    @GetMapping("/procesos/{id}/cargos")
    public String verCargosYCandidatos(@PathVariable Long id, Model model) {
        ProcesoElectoral proceso = procesoRepo.findById(id).orElse(null);
        if (proceso != null) {
            model.addAttribute("cargos", proceso.getCargos());
        }
        return "votante_cargos";
    }

 
    @GetMapping("/panel")
    public String panelVotante(Model model, HttpSession session) {
        Long votanteId = (Long) session.getAttribute("usuarioId");
        Votante votante = votanteRepo.findById(votanteId).orElse(null);

        Voto voto = votoRepo.findByVotante(votante).orElse(null);
        ProcesoElectoral proceso = (voto != null) ? voto.getProcesoElectoral() : null;
        boolean haVotado = (voto != null);

        List<Candidato> candidatos = (proceso != null) ? 
            candidatoRepo.findByProcesoElectoral(proceso) : 
            List.of();

        model.addAttribute("votante", votante);
        model.addAttribute("proceso", proceso);
        model.addAttribute("candidatos", candidatos);
        model.addAttribute("haVotado", haVotado);
        model.addAttribute("voto", voto);

        return "panel_votante"; 
    }

    
    @PostMapping("/votar")
    public String votar(@RequestParam Long votanteId,
                        @RequestParam Long procesoId,
                        @RequestParam Long candidatoId) {
        Votante votante = votanteRepo.findById(votanteId).orElse(null);
        Candidato candidato = candidatoRepo.findById(candidatoId).orElse(null);
        ProcesoElectoral proceso = procesoRepo.findById(procesoId).orElse(null);

        if (votante != null && candidato != null && proceso != null) {
            boolean yaVoto = votoRepo.findByVotante(votante)
                .map(v -> v.getProcesoElectoral().getId().equals(procesoId))
                .orElse(false);

            if (!yaVoto) {
                Voto voto = new Voto();
                voto.setVotante(votante);
                voto.setCandidato(candidato);
                voto.setProcesoElectoral(proceso);
                votoRepo.save(voto);
            }
        }

        return "redirect:/votante/panel";
    }


}
