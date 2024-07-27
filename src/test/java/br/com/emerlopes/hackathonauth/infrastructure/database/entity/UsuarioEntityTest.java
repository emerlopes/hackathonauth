package br.com.emerlopes.hackathonauth.infrastructure.database.entity;

import br.com.emerlopes.hackathonauth.domain.shared.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioEntityTest {

    @Test
    void testGetAuthoritiesAdmin() {
        // Arrange
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .username("adminUser")
                .password("adminPassword")
                .role(Role.ADMIN)
                .build();

        // Act
        Collection<? extends GrantedAuthority> authorities = usuarioEntity.getAuthorities();

        // Assert
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Role.ADMIN.getRole())));
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Role.USER.getRole())));
    }

    @Test
    void testGetAuthoritiesUser() {
        // Arrange
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .username("regularUser")
                .password("userPassword")
                .role(Role.USER)
                .build();

        // Act
        Collection<? extends GrantedAuthority> authorities = usuarioEntity.getAuthorities();

        // Assert
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Role.USER.getRole())));
    }

    @Test
    void testGetAuthoritiesGuest() {
        // Arrange
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .username("guestUser")
                .password("guestPassword")
                .role(Role.GUEST)
                .build();

        // Act
        Collection<? extends GrantedAuthority> authorities = usuarioEntity.getAuthorities();

        // Assert
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Role.GUEST.getRole())));
    }

    @Test
    void testGetAuthoritiesNullRole() {
        // Arrange
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .username("nullRoleUser")
                .password("nullRolePassword")
                .role(null)
                .build();

        // Act
        Collection<? extends GrantedAuthority> authorities = usuarioEntity.getAuthorities();

        // Assert
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Role.GUEST.getRole())));
    }

    @Test
    void testGetPassword() {
        // Arrange
        String password = "userPassword";
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .password(password)
                .build();

        // Act & Assert
        assertEquals(password, usuarioEntity.getPassword());
    }

    @Test
    void testGetUsername() {
        // Arrange
        String username = "user";
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .username(username)
                .build();

        // Act & Assert
        assertEquals(username, usuarioEntity.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        // Arrange
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().build();

        // Act & Assert
        assertTrue(usuarioEntity.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        // Arrange
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().build();

        // Act & Assert
        assertTrue(usuarioEntity.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Arrange
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().build();

        // Act & Assert
        assertTrue(usuarioEntity.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        // Arrange
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().build();

        // Act & Assert
        assertTrue(usuarioEntity.isEnabled());
    }
}