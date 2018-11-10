package me.sehwa.supremeboard.service;

import me.sehwa.supremeboard.domain.Comment;
import me.sehwa.supremeboard.exception.ServiceException;
import me.sehwa.supremeboard.util.Pagination;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByBoard(Pagination pagination, Long boardId) throws ServiceException;

    Long addComment(Comment comment) throws ServiceException;
}
