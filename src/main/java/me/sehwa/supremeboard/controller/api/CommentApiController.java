package me.sehwa.supremeboard.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.sehwa.supremeboard.domain.Comment;
import me.sehwa.supremeboard.exception.ControllerException;
import me.sehwa.supremeboard.service.CommentService;
import me.sehwa.supremeboard.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/comments")
@Slf4j
public class CommentApiController {

    @Autowired
    private CommentService commentService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity getComments(@RequestBody String comment) throws ControllerException {
        Comment aComment;
        try {
            aComment = objectMapper.readValue(comment, Comment.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
        commentService.addComment(aComment);
        List<Comment> comments = commentService.getCommentsByBoard(new Pagination(), aComment.getBoardId());
        return ResponseEntity.ok(comments);
    }
}
