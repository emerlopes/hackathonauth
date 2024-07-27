package br.com.emerlopes.hackathonauth.domain.usecase;

import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.shared.ExecutionUseCase;

public interface ValidarTokenUseCase extends ExecutionUseCase<AutenticacaoDomainEntity, AutenticacaoDomainEntity> {
}
