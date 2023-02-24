package com.gestion.empleados.controlador;

import com.gestion.empleados.entidades.Departamento;
import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.Mensajeria;
import com.gestion.empleados.entidades.Usuario;
import com.gestion.empleados.repositorios.EmpleadoRepository;
import com.gestion.empleados.servicio.DepartamentoService;
import com.gestion.empleados.servicio.EmpleadoService;
import com.gestion.empleados.servicio.MensajeService;
import com.gestion.empleados.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {


    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private MensajeService mensajeService;

    static UserDetails usuario;

    public static Usuario usuarioActual;
    public static Empleado empleadoGlobal;


    @GetMapping("/")
    public String cargar() {
//        List<Mensajeria> mensajeriaList;
//        mensajeriaList = mensajeService.findAll();
//        model.addAttribute("mensajes", mensajeriaList);
        return "redirect:/home";
    }


    @GetMapping("/home")
    public String cargarHome(Model model) {
        List<Mensajeria> mensajeriaList;
        mensajeriaList = mensajeService.findAll();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        empleadoGlobal = empleadoService.findByName(username);

        model.addAttribute("mensajes", mensajeriaList);
        model.addAttribute("empleado", empleadoGlobal );
        return "home";
    }

    @GetMapping("/show")
    public String register() {
        return "redirect:/login";
    }


    @GetMapping("/listarperfil")
    public String listarDatosPersonales(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        Empleado empleado = empleadoService.findByName(username);

        empleadoGlobal =  empleado;
        List<Departamento> departamentos = departamentoService.findAll();

        String nombreDepartamento = null;
        for (Departamento departamento: departamentos) {
            if (departamento.getIdDepartamento() == empleado.getIdDepartamento()) {
                nombreDepartamento = departamento.getNombre();
                break;
            }
        }

        model.addAttribute("titulo",   username.toUpperCase()  + " aqui puedes observar los datos de tu perfil");
        model.addAttribute("empleado", empleado );
        model.addAttribute("departamento", nombreDepartamento );

        return "listarperfil";
    }



    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
        Empleado empleado;
        List<Departamento> departamentos = departamentoService.findAll();
        empleado = empleadoService.findOne(id);
        modelo.put("empleado",empleado);
        modelo.put("departamentos", departamentos);
        modelo.put("titulo", "Bienvenido " + usuario.getUsername() + " estos son tus datos");
        return "form";
    }


    @ControllerAdvice
    public static class CurrentUserControllerAdvice {
        @ModelAttribute("currentUser")
        public UserDetails getCurrentUser(Authentication authentication) {
            usuario = (UserDetails) authentication.getPrincipal();
            return (UserDetails) authentication.getPrincipal();
        }
    }

    public static Empleado getEmpleadoGlobal() {
        return empleadoGlobal;
    }


}
