package me.sehwa.supremeboard.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAccessDate;
    private UserStatus status;
    private List<UserRole> roles = new ArrayList<>();

    public User() {
        // 디폴트 생성자가 없으면 row mapper 가 매핑을 못하더라고...
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.status = UserStatus.ENABLED;
    }

    public String getStatus() {
        return status.name();
    }

    private void addRole(UserRole role) {
        roles.add(role);
    }
}
