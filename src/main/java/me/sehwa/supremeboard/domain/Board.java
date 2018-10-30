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
    private Long hit;
    private Long commentCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long user_id;
    private Long category_id;

    @Builder
    public Board(String title, String writer, String content, Long user_id, Long category_id) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.user_id = user_id;
        this.category_id = category_id;
    }
}
