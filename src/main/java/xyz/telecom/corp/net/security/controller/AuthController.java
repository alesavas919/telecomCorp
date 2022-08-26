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

import xyz.telecom.corp.net.security.dto.JwtDto;
import xyz.telecom.corp.net.security.dto.LoginUser;
import xyz.telecom.corp.net.security.dto.newUser;
import xyz.telecom.corp.net.security.entity.Rol;
import xyz.telecom.corp.net.security.entity.Users;
import xyz.telecom.corp.net.security.enums.RolNombre;
import xyz.telecom.corp.net.security.jwt.JwtProvider;
import xyz.telecom.corp.net.security.service.RolService;
import xyz.telecom.corp.net.security.service.UsuarioService;

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
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private JwtProvider jwtProvider;
    
    //ABOUT INIT


    //END INIT
    
    
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@RequestBody newUser newUser, BindingResult bindingResult){

			
		Rol rol = new Rol();
		rol.setId(Short.parseShort("1"));
		rol.setRolNombre(RolNombre.ROLE_ADMIN);
		rolService.save(rol);
		rol.setId(Short.parseShort("2"));
		rol.setRolNombre(RolNombre.ROLE_USER);
		rolService.save(rol);
		
    	if(bindingResult.hasErrors())
            return new ResponseEntity("campos mal puestos o email inválido", HttpStatus.INTERNAL_SERVER_ERROR);
        if(usuarioService.existsByNombreUsuario(newUser.getLogin()))
            return new ResponseEntity("Ese nombre ya existe", HttpStatus.CONFLICT);
        if(usuarioService.existsByEmail(newUser.getEmail()))
            return new ResponseEntity("Ese email ya existe", HttpStatus.BAD_REQUEST);
        Users users =
                new Users(newUser.getName(), newUser.getLogin(), newUser.getEmail(),newUser.getCode(),newUser.getPhoneNumber(),newUser.getAddress(),
                        passwordEncoder.encode(newUser.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(newUser.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        users.setRoles(roles);
        usuarioService.save(users);
        return ResponseEntity.ok("usuario guardado");
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
}
