package xyz.telecom.corp.net.security.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import xyz.telecom.corp.net.dto.CustomUserDto;
import xyz.telecom.corp.net.security.dto.JwtDto;
import xyz.telecom.corp.net.security.dto.LoginUser;
import xyz.telecom.corp.net.security.dto.newUser;
import xyz.telecom.corp.net.security.entity.Rol;
import xyz.telecom.corp.net.security.entity.Users;
import xyz.telecom.corp.net.security.enums.RolName;
import xyz.telecom.corp.net.security.jwt.JwtProvider;
import xyz.telecom.corp.net.security.service.RolService;
import xyz.telecom.corp.net.security.service.UserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RolService rolService;

    @Autowired
    private JwtProvider jwtProvider;
    
    //ABOUT INIT


    //END INIT
    
    
    @PostMapping("/newUser")
    public ResponseEntity<?> nuevo(@RequestBody newUser newUser, BindingResult bindingResult){
    	Boolean rl = rolService.getByRolName(RolName.ADMIN).isEmpty();
    	//System.out.println(rl);
		if(rl) {
			Rol rol = new Rol();
			rol.setId(Short.parseShort("1"));
			rol.setRolName(RolName.ADMIN);
			rolService.save(rol);
			rol.setId(Short.parseShort("2"));
			rol.setRolName(RolName.USER);
			rolService.save(rol);
			rol.setId(Short.parseShort("3"));
			rol.setRolName(RolName.VIEWER);
			rolService.save(rol);
		}    	
    	if(bindingResult.hasErrors())
            return new ResponseEntity("campos mal puestos o email inválido", HttpStatus.INTERNAL_SERVER_ERROR);
        if(userService.existsByUserName(newUser.getLogin()))
            return new ResponseEntity("Ese nombre ya existe", HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(newUser.getEmail()))
            return new ResponseEntity("Ese email ya existe", HttpStatus.BAD_REQUEST);
        if(userService.existsByCode(newUser.getCode()))
            return new ResponseEntity("Ese codigo ya existe", HttpStatus.BAD_REQUEST);
        if(newUser.getAddress()== null||newUser.getAge() == null||newUser.getCode()==null||newUser.getEmail()== null||
        		newUser.getLogin()== null||newUser.getPassword()== null||newUser.getPhoneNumber()== null||newUser.getRoles()== null) {
        	return new ResponseEntity("Lo sentimos, no admitimos ningun dato vacio", HttpStatus.BAD_REQUEST);
        }
        if(newUser.getAddress().isEmpty()||newUser.getAge()<14||newUser.getCode()<=0||newUser.getEmail().isEmpty()||
        		newUser.getLogin().isEmpty()||newUser.getPassword().isEmpty()||newUser.getPhoneNumber().isEmpty()||newUser.getRoles().size() <= 0) {
        	return new ResponseEntity("Lo sentimos, no admitimos ningun dato vacio", HttpStatus.BAD_REQUEST);
        }
        Users users =
                new Users(newUser.getName(), newUser.getLogin(), newUser.getEmail(),newUser.getCode(),newUser.getAge(),newUser.getPhoneNumber(),newUser.getAddress(),
                        passwordEncoder.encode(newUser.getPassword()));
        Set<Rol> roles = new HashSet<>();
        //roles.add(rolService.getByRolNombre(RolName.ADMIN).get());
        if(newUser.getRoles().contains("admin"))roles.add(rolService.getByRolName(RolName.ADMIN).get());
        if(newUser.getRoles().contains("user"))roles.add(rolService.getByRolName(RolName.USER).get());
        if(newUser.getRoles().contains("viewer"))roles.add(rolService.getByRolName(RolName.VIEWER).get());
        users.setRoles(roles);
        
        userService.save(users);
        return ResponseEntity.ok("Usuario Guardado");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())return ResponseEntity.badRequest().body("campos mal puestos "+HttpStatus.BAD_REQUEST);
        
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getLogin(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtProvider.generateToken(authentication);
        
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        
        return ResponseEntity.ok(jwtDto);
        //return ResponseEntity.ok("ok");
    }
    
    @PutMapping("/updateUser")
    public ResponseEntity<?> registerUser(@RequestBody CustomUserDto user,@PathVariable("id") Integer id, BindingResult bindingResult){
    	
    	Users usr = new Users(user.getId(),user.getCode(),user.getName(),user.getLogin(),user.getAge(),user.getPhoneNumber()
        		,user.getAddress(),user.getEmail(),passwordEncoder.encode(user.getPassword()));
    	/*
    	 Users users =
                 new Users(newUser.getName(), newUser.getLogin(), newUser.getEmail(),newUser.getCode(),newUser.getAge(),newUser.getPhoneNumber(),newUser.getAddress(),
                         passwordEncoder.encode(newUser.getPassword()));
        
         * Id,Integer code,String name,String login,Short age,String phoneNumber
    		,String address,String email,String password
         * */
        Set<Rol> roles = new HashSet<>();
        //roles.add(rolService.getByRolNombre(RolName.ADMIN).get());
        if(usr.getRoles().contains("admin"))roles.add(rolService.getByRolName(RolName.ADMIN).get());
        if(usr.getRoles().contains("user"))roles.add(rolService.getByRolName(RolName.USER).get());
        if(usr.getRoles().contains("viewer"))roles.add(rolService.getByRolName(RolName.VIEWER).get());
        usr.setRoles(roles);
        //userService.deleteUser(id);
        
        userService.updateUserInfo(usr,user.getId());
        
        //userService.save(users);
        return ResponseEntity.ok("Usuario Actualizado");
    }
}
