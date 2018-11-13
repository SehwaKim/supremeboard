package me.sehwa.supremeboard.service.impl;

import me.sehwa.supremeboard.dao.BoardDao;
import me.sehwa.supremeboard.dao.CommentDao;
import me.sehwa.supremeboard.domain.Comment;
import me.sehwa.supremeboard.exception.ServiceException;
import me.sehwa.supremeboard.service.CommentService;
import me.sehwa.supremeboard.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BoardDao boardDao;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBoard(Pagination pagination, Long boardId) throws ServiceException {
        try {
            return commentDao.selectAll(pagination, boardId);
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public Long addComment(Comment aComment) throws ServiceException{
        try {
            if (Objects.nonNull(aComment.getParentId())) {
                Comment parentComment = commentDao.selectOneById(aComment.getParentId());
                Long family = Optional.ofNullable(parentComment.getFamily()).orElse(parentComment.getId());
                aComment.setFamily(family);
                aComment.setFamilySeq(parentComment.getFamilySeq() + 1);
                aComment.setIndent(parentComment.getIndent() + 1);
                commentDao.updateFamilySeq(aComment.getBoardId(), aComment.getFamily(), parentComment.getFamilySeq());
            }
            Long id = commentDao.insert(aComment);
            if (Objects.isNull(aComment.getParentId())) {
                commentDao.updateFamily(id);
            }
            boardDao.updateCommentCnt(aComment.getBoardId());
            return id;
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }
}
