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

    private Board createTestBoard() {
        return Board.builder().categoryId(1L).userId(1L).title("안녕하세요").content("배고파밥줘").writer("김세화").build();
    }
}
