package xyz.telecom.corp.net.security.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class newUser {
    
    private String name;
    
    private String login;
    
    private String email;
    
    private Integer code;
    
    private String phoneNumber;
    
    private String address;
    
    private String password;
    private Set<String> roles = new HashSet();

    

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
