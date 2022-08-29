package xyz.telecom.corp.net.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class CustomUserDto {
    private Integer id;
	
	private String name;
    
    private String login;
    
    private String email;
    
    private Integer code;
    
    private String phoneNumber;

    private Short age;
    
    private String address;
    
    private String password;
    private Set<String> roles = new HashSet();
    
}
