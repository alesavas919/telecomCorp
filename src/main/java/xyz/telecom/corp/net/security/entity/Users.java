package xyz.telecom.corp.net.security.entity;



import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer code;
    
    private String name;
    @Column(unique = true)
    private String login;
    
    private String phoneNumber;
    private String address;
    
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    public Users() {
    }

    public Users(@NotNull String name, @NotNull String login, @NotNull String email, Integer code,String phoneNumber,String address,@NotNull String password) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
