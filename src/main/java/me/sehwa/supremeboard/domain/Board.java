package me.sehwa.supremeboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Board {
    private Long id;
    private Long boardId; // id와 boardId는 동일한 것임 (board_content 에 board_id를 위한 namedParameter 로 쓰기위해서)
    private String title;
    private String writer;
    private String content;
    private long hit;
    private long commentCnt;
    private LocalDateTime createdAt;
    private LocalDate updatedAt;
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
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDate.now();
    }
}
