package xyz.telecom.corp.net.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.telecom.corp.net.security.entity.Users;

@Repository
public interface UsuarioRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);

}
