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
    private Long boardId;
    private Long userId;

    public Comment() {}

    @Builder
    public Comment(String commenter, String content, Long boardId, Long userId) {
        this.commenter = commenter;
        this.content = content;
        this.boardId = boardId;
        this.userId = userId;
    }
}
