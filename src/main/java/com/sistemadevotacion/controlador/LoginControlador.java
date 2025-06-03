package com.sistemadevotacion.controlador;

import com.sistemadevotacion.modelo.Credencial;
import com.sistemadevotacion.modelo.rol;
import com.sistemadevotacion.servicio.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


import java.util.Optional;

@Controller
public class LoginControlador {

    @Autowired
    private loginService loginServicio;

    @GetMapping("/login")
    public String mostrarFormulario() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                 @RequestParam String contraseña,
                                 Model model,
                                 HttpSession session) {
        Optional<Credencial> credencialOpt = loginServicio.autenticar(correo, contraseña);

        if (credencialOpt.isPresent()) {
            rol rol = credencialOpt.get().getRol();
            Long usuarioId = credencialOpt.get().getUsuarioId();

            session.setAttribute("usuarioId", usuarioId);
            session.setAttribute("rol", rol.name());

            switch (rol) {
            case ADMINISTRADOR:
                return "redirect:/admin/panel";
            case MODERADOR:
                return "redirect:/moderador/panel";
            case SUPERVISOR:
                return "redirect:/supervisor/panel";
            case CANDIDATO:
                return "redirect:/candidato/panel";
            case VOTANTE:
                return "redirect:/votante/panel";
        }
        }

        model.addAttribute("error", "Correo o contraseña inválidos");
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login"; 
    }

}