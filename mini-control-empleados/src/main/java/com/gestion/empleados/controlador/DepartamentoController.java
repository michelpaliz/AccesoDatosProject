package com.gestion.empleados.controlador;

import com.gestion.empleados.entidades.Departamento;
import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.servicio.DepartamentoService;
import com.gestion.empleados.util.paginacion.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = {"/departamento"})
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;


    @GetMapping("/form")
    public String mostrarFormularioDeRegistrarDepartamento(Map<String,Object> modelo) {
        Departamento departamento = new Departamento();
        modelo.put("departamento", departamento);
        modelo.put("titulo", "Registro de departamentos");
        return "formdep";
    }


    @PostMapping("/form")
    public String guardarDepartamento(@Valid Departamento departamento,BindingResult result,Model modelo,RedirectAttributes flash,SessionStatus status) {
        if(result.hasErrors()) {
            modelo.addAttribute("titulo", "Registro de departamento");
            return "formdep";
        }

        String mensaje = (departamento.getIdDepartamento() != null) ? "El departamento ha sido editado con exito" : "Departamento registrado con exito";

        departamentoService.save(departamento);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/departamento/listar";
//        return "listardep";
    }


    @GetMapping({"/","/listar",""})
    public String listarDepartamentos(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Departamento> departamentos = departamentoService.findAll(pageRequest);
        PageRender<Departamento> pageRender = new PageRender<>("/listar", departamentos);

        modelo.addAttribute("titulo","Listado de departamentos");
        modelo.addAttribute("departamentos",departamentos);
        modelo.addAttribute("page", pageRender);

        return "listardep";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
        if(id > 0) {
            departamentoService.delete(id);
            flash.addFlashAttribute("success", "Empleado eliminado con exito");
        }
        return "redirect:/departamento/listar";
//        return "listardep";
    }

    @GetMapping("/form/{id}")
    public String editarDepartamento(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
        Departamento departamento;
        if(id > 0) {
            departamento = departamentoService.findOne(id);
            if(departamento == null) {
                flash.addFlashAttribute("error", "El ID del departamento no existe en la base de datos");
                return "redirect:/departamento/listar";
            }
        }
        else {
            flash.addFlashAttribute("error", "El ID del departamento no puede ser cero");
            return "redirect:/departamento/listar";
        }

        modelo.put("departamento",departamento);
        modelo.put("titulo", "Edición de departamento");
        return "formdep";
    }


}
