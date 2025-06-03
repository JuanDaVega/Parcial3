
package com.sistemadevotacion.servicio;

import com.sistemadevotacion.modelo.Credencial;
import com.sistemadevotacion.repository.credencialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class loginService {

    @Autowired
    private credencialRepositorio credencialRepositorio;

    public Optional<Credencial> autenticar(String correo, String contraseña) {
        Optional<Credencial> credencialOpt = credencialRepositorio.findByCorreo(correo);
        if (credencialOpt.isPresent() && credencialOpt.get().getContrasena().equals(contraseña)) {
            return credencialOpt;
        }
        return Optional.empty();
    }
}
