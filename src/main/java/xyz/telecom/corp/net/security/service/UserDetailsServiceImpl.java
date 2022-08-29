package xyz.telecom.corp.net.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xyz.telecom.corp.net.security.entity.Users;
import xyz.telecom.corp.net.security.entity.PrincipalUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Users users = userService.getByUserName(nombreUsuario).get();
        return PrincipalUser.build(users);
    }
}
