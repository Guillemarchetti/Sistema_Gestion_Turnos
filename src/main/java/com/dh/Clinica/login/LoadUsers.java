package com.dh.Clinica.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoadUsers implements ApplicationRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passAdmin = passwordEncoder.encode("admin");
        String passUser = passwordEncoder.encode("user");
        Usuario admin = new Usuario("Guillermina Marchetti","admin","admin@gmail.com",passAdmin,Rol.ADMIN);
        Usuario user = new Usuario("Juan Perez","user","user@gmail.com",passUser,Rol.USER);
        usuarioRepository.save(admin);
        usuarioRepository.save(user);
    }

}
