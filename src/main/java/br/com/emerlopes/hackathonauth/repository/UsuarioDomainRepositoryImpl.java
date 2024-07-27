package br.com.emerlopes.hackathonauth.repository;

import br.com.emerlopes.hackathonauth.domain.entity.UsuarioDomainEntity;
import br.com.emerlopes.hackathonauth.domain.shared.Role;
import br.com.emerlopes.hackathonauth.infrastructure.database.entity.UsuarioEntity;
import br.com.emerlopes.hackathonauth.infrastructure.database.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDomainRepositoryImpl {

    private final static Logger log = LoggerFactory.getLogger(UsuarioDomainRepositoryImpl.class);
    private final UsuarioRepository usuarioRepository;

    @Value("${application.user}")
    private String user;

    @Value("${application.password}")
    private String password;

    public UsuarioDomainRepositoryImpl(
            final UsuarioRepository usuarioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostConstruct
    public void init() {
        log.info("Registrando usuario administrador padrão");
        final var usuarioDomainEntity = UsuarioDomainEntity.builder()
                .usename(this.user)
                .password(this.password)
                .role(Role.ADMIN)
                .build();

        final var senhaCriptografada = new BCryptPasswordEncoder().encode(
                usuarioDomainEntity.getPassword());

        usuarioDomainEntity.setPassword(senhaCriptografada);

        final UsuarioEntity aplicacaoEntidadeConstruida = UsuarioEntity.builder()
                .username(usuarioDomainEntity.getUsename())
                .password(usuarioDomainEntity.getPassword())
                .role(usuarioDomainEntity.getRole())
                .build();

        final UsuarioEntity aplicacaoEntidadeGerada = usuarioRepository.save(aplicacaoEntidadeConstruida);

    }

}
