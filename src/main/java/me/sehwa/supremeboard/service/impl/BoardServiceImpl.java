package me.sehwa.supremeboard.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.sehwa.supremeboard.dao.BoardDao;
import me.sehwa.supremeboard.domain.Board;
import me.sehwa.supremeboard.exception.ServiceException;
import me.sehwa.supremeboard.service.BoardService;
import me.sehwa.supremeboard.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Override
    @Transactional(readOnly = true)
    public List<Board> getBoards(Pagination pagination, Long categoryId, String[] searchTypes, String searchStr) throws ServiceException {
        try {
            return boardDao.selectAll(pagination, categoryId, searchTypes, searchStr);
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public Long writeBoard(Board aBoard) throws ServiceException {
        try {
            if (Objects.nonNull(aBoard.getParentId())) {
                // 답글일 경우 parentId 를 가지고서
                // family, familySeq, indent 다 찾아서(+계산해서) 넣어줘야 한다.
                Board parentBoard = boardDao.selectOneById(aBoard.getParentId());
                Long family = Optional.ofNullable(parentBoard.getFamily()).orElse(parentBoard.getId());
                aBoard.setFamily(family);
                aBoard.setFamilySeq(parentBoard.getFamilySeq() + 1);
                aBoard.setIndent(parentBoard.getIndent() + 1);
                // 새 답글이 끼어들 자리 이하의 글들에 대해서 family_seq + 1 해주기
                boardDao.updateFamilySeq(parentBoard.getFamily(), parentBoard.getFamilySeq());
            }

            Long newBoardId = boardDao.insert(aBoard);
            if (Objects.isNull(aBoard.getParentId())) {
                boardDao.updateFamily(newBoardId);
            }
            aBoard.setBoardId(newBoardId);
            return boardDao.insertContent(aBoard);

        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Board getBoardById(Long id) throws ServiceException {
        try {
            return boardDao.selectOneById(id);
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getTotalPostSize(String[] searchTypes, String searchStr) throws ServiceException {
        try {
            return boardDao.countAll(searchTypes, searchStr);
        } catch (RuntimeException e) {
            throw new ServiceException(e);
        }
    }
}
