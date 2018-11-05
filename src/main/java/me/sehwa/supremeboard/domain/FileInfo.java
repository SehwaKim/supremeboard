package me.sehwa.supremeboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FileInfo {
    private Long id;
    private String name;
    private String path;
    private Long size;
    private String type;
    private LocalDateTime createdAt;
    private Long boardId;

    @Builder
    public FileInfo(String name, String path, Long size, String type, Long boardId) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.type = type;
        this.boardId = boardId;
    }
}
