package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.config.DBConfig;
import me.sehwa.supremeboard.domain.Comment;
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
public class CommentDaoTest {

    @Autowired
    DataSource dataSource;
    CommentDao commentDao;

    @Before
    public void init() {
        commentDao = new CommentDao(dataSource);
    }

    @Test
    public void dataSourceTest() {
        assertThat(dataSource).isNotNull();
    }

    @Test
    public void insertTest() {
        //given
        Comment aComment = createTestComment();
        // when
        Long id = commentDao.insert(aComment);
        Comment theComment = commentDao.selectOneById(id);
        // then
        assertThat(theComment.getId()).isEqualTo(id);
        assertThat(theComment.getBoardId()).isEqualTo(aComment.getBoardId());
        assertThat(theComment.getCommenter()).isEqualTo(aComment.getCommenter());
        assertThat(theComment.getContent()).isEqualTo(aComment.getContent());
        assertThat(theComment.getUserId()).isEqualTo(aComment.getUserId());
    }

    @Test
    public void selectOneByIdTest() {
        // given
        Long id = commentDao.insert(createTestComment());
        // when
        Comment theComment = commentDao.selectOneById(id);
        // then
        assertThat(theComment).isNotNull();
        assertThat(theComment.getId()).isEqualTo(id);
    }

    @Test
    public void selectAllTest() {
        // when
        List<Comment> list = commentDao.selectAll(null, 0L);
        // then
        assertThat(list).isNotNull();
        assertThat(list.isEmpty()).isTrue();

        // given
        commentDao.insert(createTestComment());
        // when
        list = commentDao.selectAll(null, 1L);
        // then
        assertThat(list.isEmpty()).isFalse();
    }

    @Test
    public void updateContentTest() {
        // given
        Long id = commentDao.insert(createTestComment());
        // when
        int count = commentDao.updateContent("내용이 바뀌었다", id);
        // then
        assertThat(count).isEqualTo(1);
        Comment theComment = commentDao.selectOneById(id);
        assertThat(theComment.getContent()).isEqualTo("내용이 바뀌었다");
    }

    private Comment createTestComment() {
        return Comment.builder().boardId(1L).commenter("김세화").content("1빠").userId(1L).build();
    }
}
