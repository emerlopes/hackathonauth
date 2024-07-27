package br.com.emerlopes.hackathonauth.domain.shared;

public interface ExecutionUseCase<T, J> {
    T execute(J domainObject);
}
