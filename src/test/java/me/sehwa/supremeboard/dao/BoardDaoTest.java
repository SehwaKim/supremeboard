package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.config.DBConfig;
import me.sehwa.supremeboard.domain.Board;
import me.sehwa.supremeboard.exception.RepositoryException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

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
        Board theBoard = boardDao.selectOneById(id);
        assertThat(theBoard.getId()).isEqualTo(id);
        assertThat(theBoard.getTitle()).isEqualTo(aBoard.getTitle());
        assertThat(theBoard.getWriter()).isEqualTo(aBoard.getWriter());
        assertThat(theBoard.getUserId()).isEqualTo(aBoard.getUserId());
        assertThat(theBoard.getCategoryId()).isEqualTo(aBoard.getCategoryId());
    }

    @Test
    public void insertContentTest() {
        // given
        Board aBoard = createTestBoard();
        // when
        Long id = boardDao.insert(aBoard);
        aBoard.setBoardId(id);
        boardDao.insertContent(aBoard);
        // then
        Board theBoard = boardDao.selectOneById(id);
        assertThat(theBoard.getContent()).isEqualTo(aBoard.getContent());
    }

    @Test
    public void updateFamilySeqTest() {
        // given
        Board parentBoard = createTestBoard();
        parentBoard = boardDao.selectOneById(boardDao.insert(parentBoard));

        Board reply1 = createTestBoard();
        reply1.setFamily(parentBoard.getId());
        reply1.setFamilySeq(2);
        reply1.setIndent(1);
        reply1.setParentId(parentBoard.getId());
        Long reply1Id = boardDao.insert(reply1);

        Board reply2 = createTestBoard();
        reply2.setFamily(parentBoard.getId());
        reply2.setFamilySeq(3);
        reply2.setIndent(2);
        reply2.setParentId(reply1Id);
        Long reply2Id = boardDao.insert(reply2);

        // when
        boardDao.updateFamilySeq(parentBoard.getId(), parentBoard.getFamilySeq());

        // then
        Board updatedReply1 = boardDao.selectOneById(reply1Id);
        assertThat(updatedReply1.getFamilySeq()).isEqualTo(reply1.getFamilySeq() + 1);
        Board updatedReply2 = boardDao.selectOneById(reply2Id);
        assertThat(updatedReply2.getFamilySeq()).isEqualTo(reply2.getFamilySeq() + 1);
    }

    @Test
    public void selectByIdTest() {
        // given
        Long id = boardDao.insert(createTestBoard());
        // when
        Board theBoard = boardDao.selectOneById(id);
        // then
        assertThat(theBoard).isNotNull();
        assertThat(theBoard.getId()).isEqualTo(id);
    }

    @Test
    public void selectAllTest() {
        // given
        boardDao.insert(createTestBoard());
        // when
        List<Board> list = boardDao.selectAll(null, 1L, null, null);
        // then
        assertThat(list).isNotEmpty();
    }

    @Test
    public void selectAllSearchedByWriterTest() {
        // given
        boardDao.insert(createTestBoard());
        // when
        String searchStr = "세화";
        List<Board> searchedList = boardDao.selectAll(null, 1L, new String[]{"writer"}, searchStr);
        // then
        assertThat(searchedList).isNotEmpty();
    }

    @Test
    public void selectAllSearchedByTitleTest() {
        // given
        boardDao.insert(createTestBoard());
        // when
        String searchStr = "안녕";
        List<Board> searchedList = boardDao.selectAll(null, 1L, new String[]{"title"}, searchStr);
        // then
        assertThat(searchedList.isEmpty()).isFalse();
    }

    @Test
    public void selectAllSearchedByContentTest() {
        // given - 기존에 저장된 board_content 레코드로 조회하자
        // when
        String searchStr = "누가";
        List<Board> searchedList = boardDao.selectAll(null, 1L, new String[]{"content"}, searchStr);
        // then
        assertThat(searchedList.isEmpty()).isFalse();
    }

    @Test
    public void selectAllSearchedByTitleAndContentTest() {
        // given
        boardDao.insert(createTestBoard());
        // when
        String searchStr = "누가"; // 기존에 저장된 board_content 레코드로 조회
        List<Board> searchedList = boardDao.selectAll(null, 1L, new String[]{"content", "title"}, searchStr);
        // then
        assertThat(searchedList.isEmpty()).isFalse();
        // when
        searchStr = "안녕";
        searchedList = boardDao.selectAll(null, 1L, new String[]{"content", "title"}, searchStr);
        // then
        assertThat(searchedList.isEmpty()).isFalse();
    }

    @Test(expected = RepositoryException.class)
    public void selectAllSearchedByInvalidSearchTypeTest() {
        String searchType = "title; DELETE * board WHERE '1' = '1';";
        String searchStr = "안녕";
        boardDao.selectAll(null, 1L, new String[]{searchType}, searchStr);
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
        Board theBoard = boardDao.selectOneById(id);
        assertThat(theBoard.getTitle()).isEqualTo(newTitle);
    }

    @Test
    public void updateHitTest() {
        // given
        Long id = boardDao.insert(createTestBoard());
        Board theBoard = boardDao.selectOneById(id);
        // when
        int count = boardDao.updateHit(id);
        // then
        assertThat(count).isEqualTo(1);
        Board updated = boardDao.selectOneById(id);
        assertThat(updated.getHit()).isEqualTo(theBoard.getHit() + 1);
    }

    @Test
    public void updateCommentCntTest() {
        // given
        Long id = boardDao.insert(createTestBoard());
        Board theBoard = boardDao.selectOneById(id);
        // when
        int count = boardDao.updateCommentCnt(id);
        // then
        assertThat(count).isEqualTo(1);
        Board updated = boardDao.selectOneById(id);
        assertThat(updated.getCommentCnt()).isEqualTo(theBoard.getCommentCnt() + 1);
    }

    private Board createTestBoard() {
        return Board.builder()
                .categoryId(1L)
                .userId(1L)
                .title("안녕하세요")
                .content("배고파밥줘")
                .writer("김세화")
                .build();
    }
}
