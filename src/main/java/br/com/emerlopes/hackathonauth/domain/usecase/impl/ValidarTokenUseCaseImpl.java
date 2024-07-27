package br.com.emerlopes.hackathonauth.domain.usecase.impl;

import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.repository.AutenticacaoDomainRepository;
import br.com.emerlopes.hackathonauth.domain.usecase.ValidarTokenUseCase;
import org.springframework.stereotype.Service;

@Service
public class ValidarTokenUseCaseImpl implements ValidarTokenUseCase {

    private final AutenticacaoDomainRepository autenticacaoDomainRepository;

    public ValidarTokenUseCaseImpl(
            final AutenticacaoDomainRepository autenticacaoDomainRepository
    ) {
        this.autenticacaoDomainRepository = autenticacaoDomainRepository;
    }

    @Override
    public AutenticacaoDomainEntity execute(
            final AutenticacaoDomainEntity autenticacaoDomainEntity
    ) {
        return autenticacaoDomainRepository.validarTokenAcesso(autenticacaoDomainEntity);
    }
}
