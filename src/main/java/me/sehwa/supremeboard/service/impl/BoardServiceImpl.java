package me.sehwa.supremeboard.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.sehwa.supremeboard.dao.BoardDao;
import me.sehwa.supremeboard.domain.Board;
import me.sehwa.supremeboard.exception.ServiceException;
import me.sehwa.supremeboard.service.BoardService;
import me.sehwa.supremeboard.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Override
    public List<Board> getBoards(Pagination pagination, Long categoryId, String[] searchTypes, String searchStr) throws ServiceException {
        try {
            return boardDao.selectAll(pagination, categoryId, searchTypes, searchStr);
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long writeBoard(Board aBoard) throws ServiceException {
        try {
            aBoard.setBoardId(boardDao.insert(aBoard));
            return boardDao.insertContent(aBoard);
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }
}
