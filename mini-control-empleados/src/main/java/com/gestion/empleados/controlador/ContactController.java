package com.gestion.empleados.controlador;

import org.apache.commons.codec.CharEncoding;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/contact")
    public String showContactForm(){
        return "contact";
    }

    @PostMapping("/contact")
    public String sendEmail(HttpServletRequest request,
                            @RequestParam("attachment")MultipartFile multipartFile) {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String emailTo = request.getParameter("emailTo");
        String subject = request.getParameter("subject");
        String  message = request.getParameter("content");

//        SimpleMailMessage message1 = new SimpleMailMessage();
//
//        message1.setFrom("contact@shopme.com");
//        message1.setTo("michelpaliz@hotmail.com");
//        String mailSubject = name + " has sent a message";
//        String mailContent = "Sender name : " + name + " \n";
//        mailContent += "Sender email : " + email + " \n";
//        mailContent += "Sender phone number : " + phone + " \n";
//        mailContent += "Subject " + subject + " \n";
//        mailContent += "message " + message + " \n";
//
//        message1.setSubject(mailSubject);
//        message1.setText(mailContent);
//         javaMailSender.send(message1);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, CharEncoding.UTF_8);
            String mailSubject = name + " has sent a message";
            String mailContent ="<p><b>Sender name:</b> " + name + " </p>";
            mailContent += "<p><b>Sender email: </b> " + email + "</p>";
            mailContent += "<p><b>Sender phone number </b> : " + phone + "</p>";
            mailContent += "<p><b>Subject</b>: " + subject + "</p>";
            mailContent += "<p><b>Message</b>: <br> " + message + "</p>";
            //**** adding inline image ******//
            mailContent += "<hr><img src='cid:logoImage'/>";
            try{
//                mimeMessageHelper.setFrom("michelpaliz@gmail.com", "Message");
                mimeMessageHelper.setFrom(email, "Message");
//                mimeMessageHelper.setTo("michelpaliz@hotmail.com");
                mimeMessageHelper.setTo(emailTo);
                mimeMessageHelper.setSubject(mailSubject);
                //******* ADDING INLINE IMAGE *****//
                mimeMessageHelper.setText(mailContent,true);
                ClassPathResource resource = new ClassPathResource("/static/logoEmployees.png");
                mimeMessageHelper.addInline("logoImage" , resource);

                if (!multipartFile.isEmpty()){
                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                    InputStreamSource inputStreamSource = new InputStreamSource() {
                        @NonNull
                        @Override
                        public InputStream getInputStream() throws IOException {
                            return multipartFile.getInputStream();
                        }
                    };

                    mimeMessageHelper.addAttachment(fileName,inputStreamSource);
                }


                javaMailSender.send(mimeMessage);

            }catch (MessagingException m){
                m.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        return  "message";
    }
}
