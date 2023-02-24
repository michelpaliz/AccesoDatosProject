package com.gestion.empleados.controlador;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.Usuario;
import com.gestion.empleados.entidades.UsuarioDto;
import com.gestion.empleados.servicio.EmpleadoService;
import com.gestion.empleados.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserRegistrationController {

    @Autowired
    private EmpleadoService empleadoService;

    private UsuarioService usuarioService;
    public static Empleado empleadoGlobalRecogido;

    public UserRegistrationController(UsuarioService usuarioService) {
        super();
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("user")
    public Usuario userRegistrationDto() {
        return new Usuario();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("usuario") UsuarioDto registrationDto) {
        List <Empleado> empleados = empleadoService.findAll();
        usuarioService.save(registrationDto);
        Usuario usuario = usuarioService.findByEmail(registrationDto.getEmail());
        for (Empleado empleado1: empleados) {
            if (empleado1.getEmail().equalsIgnoreCase(registrationDto.getEmail()) && empleado1.getUsuario() == null){
                empleado1.setUsuario(usuario);
                empleado1.getUsuario().setEmpleado(empleado1);
                empleadoGlobalRecogido = empleado1;
                empleadoService.save(empleado1);
            }
        }
        return "redirect:/registration?success";
    }

    @GetMapping("/registration")
    public String mostrarFormularioDeRegistrarCliente(Map<String,Object> modelo) {
        UsuarioDto usuarioDto = new UsuarioDto();
        List<Empleado> empleadoList = empleadoService.findAll();
        List<Empleado> empleadosSinUsuarios = empleadoList.stream().filter(empleado -> empleado.getUsuario() == null).collect(Collectors.toList());
        System.out.println( " Esto es empleados sin usuarios  " + empleadosSinUsuarios.size());
        List<String> tipoList = Arrays.asList("ADMIN","usuario");
        modelo.put("usuario", usuarioDto);
        modelo.put("tipos", tipoList);
        modelo.put("empleados", empleadosSinUsuarios );
        modelo.put("titulo", "Registro de empleados");
        return "registration";
    }

}
