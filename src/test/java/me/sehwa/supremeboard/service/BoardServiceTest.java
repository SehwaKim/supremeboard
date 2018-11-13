package me.sehwa.supremeboard.service;

import me.sehwa.supremeboard.config.RootContextConfig;
import me.sehwa.supremeboard.dao.BoardDao;
import me.sehwa.supremeboard.domain.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RootContextConfig.class)
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardDao boardDao;

    @Test
    public void writeBoardTest() {
        // given
        Board aBoard = createTestBoard();
        // when
        Long id = boardService.writeBoard(aBoard);
        // then
        assertThat(id).isNotNull();
    }

    @Test
    public void writeBoardReplyTest() {
        /*// given
        Board parentBoard = createTestBoard();
//        Long parentId = boardService.writeBoard(parentBoard);
        Long parentId = 1L;
//        System.out.println(parentId);
//        System.out.println("======================");
//        System.out.println(boardService.getBoardById(parentId));
        Board aBoard = createTestBoard();
        aBoard.setParentId(parentId);
        aBoard.setFamily(parentId);
        aBoard.setFamilySeq(2);
        aBoard.setIndent(1);

        Board anotherBoard = createTestBoard();
        anotherBoard.setParentId(parentId);
        anotherBoard.setFamily(parentId);
        anotherBoard.setFamilySeq(2);
        anotherBoard.setIndent(1);

        // when
        Long aBoardId = boardService.writeBoard(aBoard);
        Long anotherBoardId = boardService.writeBoard(anotherBoard);

        // then
        Board aSavedBoard = boardService.getBoardById(aBoardId);
        assertThat(aSavedBoard.getParentId()).isEqualTo(parentId);
        assertThat(aSavedBoard.getFamily()).isEqualTo(parentId);
        assertThat(aSavedBoard.getFamilySeq()).isEqualTo(3);
        assertThat(aSavedBoard.getIndent()).isEqualTo(1);

        Board anotherSavedBoard = boardService.getBoardById(anotherBoardId);
        assertThat(anotherSavedBoard.getParentId()).isEqualTo(parentId);
        assertThat(anotherSavedBoard.getFamily()).isEqualTo(parentId);
        assertThat(anotherSavedBoard.getFamilySeq()).isEqualTo(2);
        assertThat(anotherSavedBoard.getIndent()).isEqualTo(1);*/
    }

    private Board createTestBoard() {
        return Board.builder().categoryId(1L).userId(1L).title("안녕하세요").content("배고파밥줘").writer("김세화").build();
    }
}
