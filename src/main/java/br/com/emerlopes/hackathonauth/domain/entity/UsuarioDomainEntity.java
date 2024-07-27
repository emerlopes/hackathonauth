package br.com.emerlopes.hackathonauth.domain.entity;

import br.com.emerlopes.hackathonauth.domain.shared.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDomainEntity {
    private UUID id;
    private String usename;
    private String password;
    private Role role;
}
