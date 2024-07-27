package br.com.emerlopes.hackathonauth.domain.entity;

import br.com.emerlopes.hackathonauth.domain.shared.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AutenticacaoDomainEntity {
    private String username;
    private String password;
    private String token;
    private String secret;
    private boolean valid;
    private List<Role> roles;
}
