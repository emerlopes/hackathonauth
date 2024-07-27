package br.com.emerlopes.hackathonauth.domain.usecase.impl;

import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.repository.AutenticacaoDomainRepository;
import br.com.emerlopes.hackathonauth.domain.shared.Role;
import br.com.emerlopes.hackathonauth.domain.usecase.AutenticacaoUseCase;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoUseCaseImpl implements AutenticacaoUseCase {

    private final AuthenticationManager authenticationManager;
    private final AutenticacaoDomainRepository autenticacaoDomainRepository;

    public AutenticacaoUseCaseImpl(
            final AuthenticationManager authenticationManager,
            final AutenticacaoDomainRepository autenticacaoDomainRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.autenticacaoDomainRepository = autenticacaoDomainRepository;
    }

    @Override
    public AutenticacaoDomainEntity execute(
            final AutenticacaoDomainEntity autenticacaoDomainEntity
    ) {
        final var userPassword = new UsernamePasswordAuthenticationToken(
                autenticacaoDomainEntity.getUsername(),
                autenticacaoDomainEntity.getPassword()
        );

        final var auth = authenticationManager.authenticate(userPassword);
        final var roles = auth.getAuthorities();

        autenticacaoDomainEntity.setRoles(
                roles.stream().map(
                        authority -> Role.fromRole(authority.getAuthority())
                ).toList()
        );

        return autenticacaoDomainRepository.gerarTokenAcesso(autenticacaoDomainEntity);

    }
}
