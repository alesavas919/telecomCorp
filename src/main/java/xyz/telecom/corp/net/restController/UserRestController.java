package xyz.telecom.corp.net.restController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.telecom.corp.net.dto.CustomUserDto;
import xyz.telecom.corp.net.security.entity.Rol;
import xyz.telecom.corp.net.security.entity.Users;
import xyz.telecom.corp.net.security.enums.RolName;
import xyz.telecom.corp.net.security.service.RolService;
import xyz.telecom.corp.net.security.service.UserService;

@RestController
@RequestMapping("user/")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@GetMapping("/findAllUsers")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findAllUsers(){
		List<Users> users = userService.findAllUsers();
		
		return ResponseEntity.ok(users);
	}
	
	@DeleteMapping("/deleteUser")
	//@PreAuthorize("ADMIN")
	public ResponseEntity<?> deleteUser(@RequestParam("id") Integer id){
		String deleted = "";
		Boolean result = userService.deleteUser(id);
		deleted = result?"El usuario fue eliminado con exito":"Lo sentimos, el usuario no existe";
		ResponseEntity resp = result?ResponseEntity.ok(deleted):ResponseEntity.badRequest().body(deleted);
		return resp;
	}
	
	@PutMapping("/updateUser")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@RequestBody CustomUserDto user){
		
        if(userService.existsByUserName(user.getLogin()))
            return new ResponseEntity("Ese nombre ya existe", HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(user.getEmail()))
            return new ResponseEntity("Ese email ya existe", HttpStatus.BAD_REQUEST);
        if(userService.existsByCode(user.getCode()))
            return new ResponseEntity("Ese codigo ya existe", HttpStatus.BAD_REQUEST);
        if(userService.existsById(user.getId())==false)
        	return new ResponseEntity("El usuario no existe",HttpStatus.BAD_REQUEST);
        
        Users usr = new Users(user.getId(),user.getCode(),user.getName(),user.getLogin(),user.getAge(),user.getPhoneNumber()
        		,user.getAddress(),user.getEmail(),passwordEncoder.encode(user.getPassword()));
        Set<Rol> roles = new HashSet<>();
        if(user.getRoles().contains("admin"))roles.add(rolService.getByRolName(RolName.ADMIN).get());
        if(user.getRoles().contains("user"))roles.add(rolService.getByRolName(RolName.USER).get());
        if(user.getRoles().contains("viewer"))roles.add(rolService.getByRolName(RolName.VIEWER).get());
        usr.setRoles(roles);
        
        
        Boolean result = userService.updateUser(usr);
		//String update = result?"Se actualizo el usuario con exito":"Lo sentimos, el usuario no existe";
		//ResponseEntity resp = result?ResponseEntity.ok(update):ResponseEntity.badRequest().body(update);
		ResponseEntity resp = ResponseEntity.ok(result);
		return resp;
	}


	//EXISTE
	@GetMapping("/roleProperties")
	public ResponseEntity<?> roleProperties(@RequestBody Users user){
		return ResponseEntity.ok("");
	}
	

}
