package me.sehwa.supremeboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Comment {
    private Long id;
    private String commenter;
    private String content;
    private LocalDateTime createdAt;
    private LocalDate updatedAt;
    private Long boardId;
    private Long userId;
    private Long parentId;
    private Long family;
    private int familySeq;
    private int indent;

    public Comment() {}

    @Builder
    public Comment(String commenter, String content, Long boardId, Long userId) {
        this.commenter = commenter;
        this.content = content;
        this.boardId = boardId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDate.now();
    }
}
