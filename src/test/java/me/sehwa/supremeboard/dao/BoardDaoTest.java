package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.config.DBConfig;
import me.sehwa.supremeboard.domain.Board;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DBConfig.class)
@Transactional
public class BoardDaoTest {

    @Autowired
    DataSource dataSource;
    BoardDao boardDao;

    @Before
    public void init() {
        boardDao = new BoardDao(dataSource);
    }

    @Test
    public void dataSourceTest() {
        assertThat(dataSource).isNotNull();
    }

    @Test
    public void insertTest() {
        // given
        Board aBoard = createTestBoard();
        // when
        Long id = boardDao.insert(aBoard);
        // then
        assertThat(id).isNotNull();
        Board theBoard = boardDao.selectById(id);
        assertThat(theBoard.getId()).isEqualTo(id);
        assertThat(theBoard.getTitle()).isEqualTo(aBoard.getTitle());
        assertThat(theBoard.getWriter()).isEqualTo(aBoard.getWriter());
        assertThat(theBoard.getUserId()).isEqualTo(aBoard.getUserId());
        assertThat(theBoard.getCategoryId()).isEqualTo(aBoard.getCategoryId());
    }

    @Test
    public void selectByIdTest() {
        // given
        Long id = boardDao.insert(createTestBoard());
        // when
        Board theBoard = boardDao.selectById(id);
        // then
        assertThat(theBoard).isNotNull();
        assertThat(theBoard.getId()).isEqualTo(id);
    }

    @Test
    public void updateTitleTest() {
        // given
        Long id = boardDao.insert(createTestBoard());
        // when
        String newTitle = "제목이 바뀜";
        int count = boardDao.updateTitle(id, newTitle);
        // then
        assertThat(count).isEqualTo(1);
        Board theBoard = boardDao.selectById(id);
        assertThat(theBoard.getTitle()).isEqualTo(newTitle);
    }

    @Test
    public void updateHitTest() {
        // given
        Long id = boardDao.insert(createTestBoard());
        Board theBoard = boardDao.selectById(id);
        // when
        int count = boardDao.updateHit(id);
        // then
        assertThat(count).isEqualTo(1);
        Board updated = boardDao.selectById(id);
        assertThat(updated.getHit()).isEqualTo(theBoard.getHit() + 1);
    }

    @Test
    public void updateCommentCntTest() {
        // given
        Long id = boardDao.insert(createTestBoard());
        Board theBoard = boardDao.selectById(id);
        // when
        int count = boardDao.updateCommentCnt(id);
        // then
        assertThat(count).isEqualTo(1);
        Board updated = boardDao.selectById(id);
        assertThat(updated.getCommentCnt()).isEqualTo(theBoard.getCommentCnt() + 1);
    }

    private Board createTestBoard() {
        return Board.builder().categoryId(1L).userId(42L).title("안녕하세요").content("배고파밥줘").writer("김세화").build();
    }
}
