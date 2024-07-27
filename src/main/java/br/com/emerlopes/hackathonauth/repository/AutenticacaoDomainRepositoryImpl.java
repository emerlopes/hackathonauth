package br.com.emerlopes.hackathonauth.repository;

import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.repository.AutenticacaoDomainRepository;
import br.com.emerlopes.hackathonauth.domain.shared.Role;
import br.com.emerlopes.hackathonauth.infrastructure.security.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoDomainRepositoryImpl implements AutenticacaoDomainRepository {

    private final TokenService tokenService;

    public AutenticacaoDomainRepositoryImpl(
            final TokenService tokenService
    ) {
        this.tokenService = tokenService;
    }

    @Override
    public AutenticacaoDomainEntity gerarTokenAcesso(final AutenticacaoDomainEntity autenticacaoDomainEntity) {

        var userDetails = User.withUsername(autenticacaoDomainEntity.getUsername())
                .password(autenticacaoDomainEntity.getPassword())
                .authorities(autenticacaoDomainEntity.getRoles().stream()
                        .map(Role::toString)
                        .toArray(String[]::new))
                .build();

        var token = tokenService.gerarToken(userDetails, autenticacaoDomainEntity.getSecret());

        return AutenticacaoDomainEntity.builder()
                .username(autenticacaoDomainEntity.getUsername())
                .token(token)
                .build();
    }

    @Override
    public AutenticacaoDomainEntity validarTokenAcesso(
            final AutenticacaoDomainEntity autenticacaoDomainEntity
    ) {
        final String isValidToken = tokenService.validarToken(autenticacaoDomainEntity.getToken());

        boolean isValid = isValidToken != null;

        return AutenticacaoDomainEntity.builder()
                .valid(isValid)
                .build();
    }
}
