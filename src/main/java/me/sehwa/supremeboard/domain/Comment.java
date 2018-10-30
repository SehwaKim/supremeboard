package me.sehwa.supremeboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {
    private Long id;
    private String commenter;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long board_id;
    private Long user_id;

    @Builder
    public Comment(String commenter, String content, Long board_id, Long user_id) {
        this.commenter = commenter;
        this.content = content;
        this.board_id = board_id;
        this.user_id = user_id;
    }
}
