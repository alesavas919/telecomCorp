package xyz.telecom.corp.net.security.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.telecom.corp.net.security.entity.Rol;
import xyz.telecom.corp.net.security.enums.RolName;
import xyz.telecom.corp.net.security.repository.RolRepository;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Optional<Rol> getByRolName(RolName rolName){
        return rolRepository.findByRolName(rolName);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}
