package xyz.telecom.corp.net.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.telecom.corp.net.security.entity.Users;
import xyz.telecom.corp.net.security.repository.UsuarioRepository;

@Service
@Transactional 
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Users> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByLogin(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByLogin(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Users users){
        usuarioRepository.save(users);
    }
}
