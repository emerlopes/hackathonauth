package br.com.emerlopes.hackathonauth.domain.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleTest {

    @Test
    void testFromRoleAdmin() {
        // Arrange
        String roleAdmin = "ROLE_ADMIN";

        // Act
        Role result = Role.fromRole(roleAdmin);

        // Assert
        assertEquals(Role.ADMIN, result);
    }

    @Test
    void testFromRoleUser() {
        // Arrange
        String roleUser = "ROLE_USER";

        // Act
        Role result = Role.fromRole(roleUser);

        // Assert
        assertEquals(Role.USER, result);
    }

    @Test
    void testFromRoleGuest() {
        // Arrange
        String roleGuest = "ROLE_GUEST";

        // Act
        Role result = Role.fromRole(roleGuest);

        // Assert
        assertEquals(Role.GUEST, result);
    }

    @Test
    void testFromRoleInvalid() {
        // Arrange
        String invalidRole = "ROLE_INVALID";

        // Act
        Role result = Role.fromRole(invalidRole);

        // Assert
        assertNull(result);
    }

    @Test
    void testRoleValues() {
        // Arrange & Act
        Role[] roles = Role.values();

        // Assert
        assertEquals(3, roles.length);
        assertEquals(Role.ADMIN, roles[0]);
        assertEquals(Role.USER, roles[1]);
        assertEquals(Role.GUEST, roles[2]);
    }
}