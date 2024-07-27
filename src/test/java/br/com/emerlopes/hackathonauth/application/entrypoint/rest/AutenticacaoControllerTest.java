package br.com.emerlopes.hackathonauth.application.entrypoint.rest;

import br.com.emerlopes.hackathonauth.domain.usecase.AutenticacaoUseCase;
import br.com.emerlopes.hackathonauth.domain.usecase.ValidarTokenUseCase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoControllerTest {

    @InjectMocks
    private AutenticacaoController autenticacaoController;

    @Mock
    private Logger logger;

    @Mock
    private AutenticacaoUseCase autenticacaoUseCase;

    @Mock
    private ValidarTokenUseCase validarTokenUseCase;

}