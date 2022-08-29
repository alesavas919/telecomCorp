package xyz.telecom.corp.net.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.telecom.corp.net.security.entity.Users;
import xyz.telecom.corp.net.security.repository.UserRepository;

@Service
@Transactional 
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Users> getByUserName(String nombreUsuario){
        return userRepository.findByLogin(nombreUsuario);
    }

    public boolean existsByUserName(String nombreUsuario){
        return userRepository.existsByLogin(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void save(Users users){
        userRepository.save(users);
    }
    
    public Boolean updateUser(Users users) {
    	Boolean resp = false;
    	if(userRepository.existsById(users.getId())) {
    		userRepository.save(users);
    		resp = true;
    	}
    	return resp;
    }
    
    public List<Users> findAllUsers(){
    	return userRepository.findAll();
    }
    public Boolean deleteUser(Integer id) {
    	Boolean deleted = false;
    	if(userRepository.existsById(id)) {
    		userRepository.deleteById(id);
    		deleted = true;
    	}
    	
    	return deleted;
    }
    public boolean existsByCode(Integer code) {
    	return userRepository.existsByCode(code);
    }
    public boolean existsById(Integer id) {
    	return userRepository.existsById(id);
    }
    public void deleteById(Integer id) {
    	userRepository.deleteById(id);
    }
}
