package me.sehwa.supremeboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Role {
    private Long id;
    private RoleName name;
    private LocalDateTime createdAt;

    public Role(RoleName name) {
        this.name = name;
    }
}
