package com.gestion.empleados.controlador;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.gestion.empleados.entidades.Departamento;
import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.Usuario;
import com.gestion.empleados.servicio.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gestion.empleados.servicio.EmpleadoService;
import com.gestion.empleados.util.paginacion.PageRender;
import com.gestion.empleados.util.reportes.EmpleadoExporterExcel;
import com.gestion.empleados.util.reportes.EmpleadoExporterPDF;
import com.lowagie.text.DocumentException;
import static com.gestion.empleados.controlador.HomeController.*;


@Controller
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private DepartamentoService departamentoService;

	private Usuario usuario;


	@GetMapping("/ver/{id}")
	public String verDetallesDelEmpleado(@PathVariable(value = "id") Long id,Map<String,Object> modelo,RedirectAttributes flash) {
		Empleado empleado = empleadoService.findOne(id);
		if(empleado == null) {
			flash.addFlashAttribute("error", "El empleado no existe en la base de datos");
			return "redirect:/listar";
		}
		
		modelo.put("empleado",empleado);
		modelo.put("titulo", "Detalles del empleado " + empleado.getNombre());
		return "ver";
	}



	@GetMapping("/filtro")
	public String filtrar(Model modelo, @Param("keyword") String keyword){
		List<Empleado> empleados;
		if (keyword == null){
			empleados = empleadoService.findAll();
		}else{
			empleados = empleadoService.filter(keyword);
		}
		modelo.addAttribute("keyword",keyword);
		modelo.addAttribute("empleados",empleados);
		return "bus";
	}

	
	@GetMapping({"/","/listar",""})
	public String listarEmpleados(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Empleado> empleados = empleadoService.findAll(pageRequest);

		Empleado empleadoPicked = getEmpleadoGlobal();

		PageRender<Empleado> pageRender = new PageRender<>("/listar", empleados);

		System.out.println("esto es usuario" + usuario);
		System.out.println("esto es usuario del empleado" + empleadoPicked.getUsuario());

		List<Departamento> departamentos = departamentoService.findAll();


		if (empleadoPicked.getUsuario().getTipo().equalsIgnoreCase("usuario")){
			List<Empleado> empleadoList = empleados.stream()
					.filter(p -> p.getIdDepartamento() == empleadoPicked.getIdDepartamento())
					.collect(Collectors.toList());
			modelo.addAttribute("titulo","Listado de empleados para el usuario");
			modelo.addAttribute("empleados",empleadoList);
			modelo.addAttribute("page", pageRender);
		}

		if (empleadoPicked.getUsuario().getTipo().equalsIgnoreCase("admin")){
			modelo.addAttribute("titulo","Listado de empleados para el administrador");
			modelo.addAttribute("empleados",empleados);
			modelo.addAttribute("page", pageRender);
		}


//		PageRender<Object> pageRender = new PageRender<>("/listar", empleados.map(empleado -> empleado.getIdDepartamento() == empleadoPicked.getIdDepartamento()));
		
		return "listar";
	}
	
	@GetMapping("/form")
	public String mostrarFormularioDeRegistrarCliente(Map<String,Object> modelo) {
		Empleado empleado = new Empleado();
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Registro de empleados");
		List<Departamento> departamentos = departamentoService.findAll();
		modelo.put("departamentos", departamentos);
//		empleado = empleadoService.findOne(empleado.getId());
//		modelo.put("empleado", empleado);
		return "form";
	}
	
	@PostMapping("/form")
	public String guardarEmpleado(@Valid Empleado empleado,BindingResult result,Model modelo,RedirectAttributes flash,SessionStatus status) {

		if(result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de cliente");
			return "form";
		}
		
		String mensaje = (empleado.getId() != null) ? "El empleado ha sido editado con exito" : "Empleado registrado con exito";

		if (empleado.getUsuario() == null){
			if (getEmpleadoGlobal().getUsuario().getEmail().equalsIgnoreCase(empleado.getEmail())){
				empleado.setUsuario(getEmpleadoGlobal().getUsuario());
			}
		}

		empleadoService.save(empleado);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);

		return "redirect:/listar";
	}
	
	@GetMapping("/form/{id}")
	public String editarEmpleado(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
		Empleado empleado;
		if(id > 0) {
			empleado = empleadoService.findOne(id);
			if(empleado == null) {
				flash.addFlashAttribute("error", "El ID del empleado no existe en la base de datos");
				return "redirect:/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID del empleado no puede ser cero");
			return "redirect:/listar";
		}

		List<Departamento> departamentos = departamentoService.findAll();

		empleado = empleadoService.findOne(empleado.getId());

		System.out.println("Empleado escogido es " + empleado.toString());

		usuario = empleado.getUsuario();



		modelo.put("departamentos", departamentos);
		modelo.put("empleado",empleado);
		modelo.put("titulo", "Edición de empleado");
		return "form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarCliente(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
		if(id > 0) {
			empleadoService.delete(id);
			flash.addFlashAttribute("success", "Empleado eliminado con exito");
		}
		return "redirect:/listar";
	}


	//*************** CREAR PDF &&& EXCEL ******************
	
	@GetMapping("/exportarPDF")
	public void exportarListadoDeEmpleadosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Empleado> empleados = empleadoService.findAll();
		
		EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleados);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarExcel")
	public void exportarListadoDeEmpleadosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".xlsx";
		
		response.setHeader(cabecera, valor);
		
		List<Empleado> empleados = empleadoService.findAll();
		
		EmpleadoExporterExcel exporter = new EmpleadoExporterExcel(empleados);
		exporter.exportar(response);
	}
}
