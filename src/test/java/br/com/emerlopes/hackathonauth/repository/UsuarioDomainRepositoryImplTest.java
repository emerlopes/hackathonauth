package br.com.emerlopes.hackathonauth.repository;

import br.com.emerlopes.hackathonauth.domain.shared.Role;
import br.com.emerlopes.hackathonauth.infrastructure.database.entity.UsuarioEntity;
import br.com.emerlopes.hackathonauth.infrastructure.database.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioDomainRepositoryImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioDomainRepositoryImpl usuarioDomainRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(usuarioDomainRepository, "user", "admin");
        ReflectionTestUtils.setField(usuarioDomainRepository, "password", "admin123");
    }

    @Test
    public void testInit() {
        UsuarioEntity savedUsuario = UsuarioEntity.builder()
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("admin123"))
                .role(Role.ADMIN)
                .build();

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(savedUsuario);

        usuarioDomainRepository.init();

        ArgumentCaptor<UsuarioEntity> usuarioCaptor = ArgumentCaptor.forClass(UsuarioEntity.class);
        verify(usuarioRepository, times(1)).save(usuarioCaptor.capture());

        UsuarioEntity usuario = usuarioCaptor.getValue();
        assertEquals("admin", usuario.getUsername());
        assertEquals(Role.ADMIN, usuario.getRole());

        assertEquals(true, new BCryptPasswordEncoder().matches("admin123", usuario.getPassword()));
    }
}
