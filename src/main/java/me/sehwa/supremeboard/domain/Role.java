package me.sehwa.supremeboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Role {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public Role(String name) {
        this.name = name;
    }
}
