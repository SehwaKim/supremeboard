package me.sehwa.supremeboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Category {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }
}
