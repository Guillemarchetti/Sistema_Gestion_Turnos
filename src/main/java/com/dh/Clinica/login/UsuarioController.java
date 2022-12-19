package com.dh.Clinica.login;

import com.dh.Clinica.service.Impl.OdontologoServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

/*

    @GetMapping()
    public ResponseEntity<?> getAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(auth.getAuthorities(), HttpStatus.OK);
    }*/
    @GetMapping()
    public void defaultAfterLogin(HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String myRole = auth.getAuthorities().toArray()[0].toString();
        if (myRole.equals("ADMIN"))
            response.sendRedirect("/indexadmin.html");
        if (myRole.equals("USER"))
            response.sendRedirect("/indexuser.html");
    }


}
