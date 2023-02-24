package com.gestion.empleados.controlador;

import com.gestion.empleados.entidades.Mensajeria;
import com.gestion.empleados.entidades.Message;
import com.gestion.empleados.servicio.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.gestion.empleados.controlador.HomeController.empleadoGlobal;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    MensajeService mensajeService;

    private Mensajeria mensajeria;

    static UserDetails usuario;

    @GetMapping("/send")
    public String mostrarFormulario() {
        return "index";
    }

    @ControllerAdvice
    public static class CurrentUserControllerAdvice {
        @ModelAttribute("currentUser")
        public UserDetails getCurrentUser(Authentication authentication) {
            usuario = (UserDetails) authentication.getPrincipal();
            return (UserDetails) authentication.getPrincipal();
        }
    }

    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Message send(final Message message) {
        Date date = new Date(System.currentTimeMillis());
        mensajeria = new Mensajeria(message.getText(), date, empleadoGlobal.getIdDepartamento(), empleadoGlobal);
        mensajeService.save(mensajeria);
        return message;
    }

    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Message message) {
        Date date = new Date(System.currentTimeMillis());
        mensajeria = new Mensajeria(message.getText(), date, empleadoGlobal.getIdDepartamento(), empleadoGlobal);
        mensajeService.save(mensajeria);
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }


}
