package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.Usuario;
import com.gestion.empleados.entidades.UsuarioDto;
import com.gestion.empleados.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Usuario findByEmail(String email) {
        List<Usuario> usuarios1 =  usuarioRepository.findAll();
        Usuario usuario = null;
        for (Usuario usuarioR: usuarios1) {
            if (usuarioR.getEmail().equalsIgnoreCase(email)){
                usuario = usuarioR;
            }
        }
        assert usuario != null;
        System.out.println("Esto es el usuario " + usuario);

        return  usuario;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario save(UsuarioDto usuarioDto) {
        Date date = new Date(System.currentTimeMillis());
        Usuario usuario = new Usuario(usuarioDto.getNombre(),usuarioDto.getEmail(),passwordEncoder.encode(usuarioDto.getPassword()),usuarioDto.getTipo(),date,usuarioDto.getEmpleado()) ;
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findByName(String name) {
        List<Usuario> usuarios =  usuarioRepository.findAll();
        Usuario usuario = null;
        for (Usuario usuarioR: usuarios) {
            if (usuarioR.getNombre().equalsIgnoreCase(name)){
                usuario = usuarioR;
            }
        }
        assert usuario != null;
        System.out.println("Esto es el usuario " + usuario);

        return  usuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = findByEmail(username);
        if (username == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User user = new User(usuario.getNombre(), usuario.getPassword(), Collections.singleton(new SimpleGrantedAuthority(usuario.getTipo())));
        System.out.println("esto es userrrr " + user);
        return user;
//        return new org.springframework.security.core.userdetails.User(usuario.getNombre(), usuario.getPassword(), Collections.singleton(new SimpleGrantedAuthority(usuario.getTipo())));
//        return new org.springframework.security.core.userdetails.User(usuario.getNombre(), usuario.getPassword(), Collections.addAll(usuario.getTipo()));
//        return (UserDetails) new Usuario(usuario.getNombre(),usuario.getEmail(), usuario.getPassword(), usuario.getTipo(),usuario.getFecha(),usuario.getEmpleado());

    }
}
