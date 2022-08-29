package xyz.telecom.corp.net.security.entity;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PrincipalUser implements UserDetails {//details about user with token 
    private String name;
    private String userName;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;//authentification

    public PrincipalUser(String name, String userName, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static PrincipalUser build(Users users){//get authorities from rol usuario
        List<GrantedAuthority> authorities =
                users.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
                .getRolName().name())).collect(Collectors.toList());
        return new PrincipalUser(users.getName(), users.getLogin(), users.getEmail(), users.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
