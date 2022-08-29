package xyz.telecom.corp.net.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.telecom.corp.net.security.entity.Rol;
import xyz.telecom.corp.net.security.enums.RolName;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(RolName rolName);
}
