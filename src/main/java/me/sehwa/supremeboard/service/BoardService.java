package me.sehwa.supremeboard.service;

import me.sehwa.supremeboard.domain.Board;
import me.sehwa.supremeboard.exception.ServiceException;
import me.sehwa.supremeboard.util.Pagination;

import java.util.List;

public interface BoardService {

    List<Board> getBoards(Pagination pagination, Long categoryId, String[] searchTypes, String searchStr) throws ServiceException;

    Long writeBoard(Board board) throws ServiceException;

    Board getBoardById(Long id) throws ServiceException;
}
