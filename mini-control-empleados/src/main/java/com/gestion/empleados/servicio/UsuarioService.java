package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Usuario;
import com.gestion.empleados.entidades.UsuarioDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService extends UserDetailsService {

    Usuario findByEmail(String username);
    List<Usuario> findAll();

    Usuario save(UsuarioDto usuarioDto);


    Usuario findByName(String name);
}
