package me.sehwa.supremeboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Board {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private long hit;
    private long commentCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private Long categoryId;

    public Board() {
    }

    @Builder
    public Board(String title, String writer, String content, Long userId, Long categoryId) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.userId = userId;
        this.categoryId = categoryId;
    }
}
