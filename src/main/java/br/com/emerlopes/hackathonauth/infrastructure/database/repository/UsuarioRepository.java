package br.com.emerlopes.hackathonauth.infrastructure.database.repository;

import br.com.emerlopes.hackathonauth.infrastructure.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UserDetails> findByUsername(String login);
}
