package br.com.emerlopes.hackathonauth.domain.repository;

import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;

public interface AutenticacaoDomainRepository {

    AutenticacaoDomainEntity gerarTokenAcesso(AutenticacaoDomainEntity autenticacaoDomainEntity);

    AutenticacaoDomainEntity validarTokenAcesso(AutenticacaoDomainEntity autenticacaoDomainEntity);
}
