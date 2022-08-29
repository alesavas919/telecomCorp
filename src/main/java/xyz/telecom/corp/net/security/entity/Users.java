package xyz.telecom.corp.net.security.entity;



import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor

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
    
    private Short age;
    private String phoneNumber;
    private String address;
    
    private String email;
    private String password;
    //it's added in the user role table automatically
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    public Users() {
    }

    public Users(@NotNull String name, @NotNull String login, @NotNull String email, Integer code,Short age,String phoneNumber,String address,@NotNull String password) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.code = code;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public Users(Integer Id,Integer code,String name,String login,Short age,String phoneNumber
    		,String address,String email,String password) {
    	this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.code = code;
        this.age = age;
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
