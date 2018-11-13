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

    @Test
    public void updateFamilySeqTest() {
        // given
        Comment parentComment = createTestComment();
        parentComment = commentDao.selectOneById(commentDao.insert(parentComment));

        Comment reply1 = createTestComment();
        reply1.setFamily(parentComment.getId());
        reply1.setFamilySeq(2);
        reply1.setIndent(1);
        reply1.setParentId(parentComment.getId());
        Long reply1Id = commentDao.insert(reply1);

        Comment reply2 = createTestComment();
        reply2.setFamily(parentComment.getId());
        reply2.setFamilySeq(3);
        reply2.setIndent(2);
        reply2.setParentId(reply1Id);
        Long reply2Id = commentDao.insert(reply2);

        // when
        commentDao.updateFamilySeq(parentComment.getBoardId(), parentComment.getId(), parentComment.getFamilySeq());

        // then
        Comment updatedReply1 = commentDao.selectOneById(reply1Id);
        assertThat(updatedReply1.getFamilySeq()).isEqualTo(reply1.getFamilySeq() + 1);
        Comment updatedReply2 = commentDao.selectOneById(reply2Id);
        assertThat(updatedReply2.getFamilySeq()).isEqualTo(reply2.getFamilySeq() + 1);
    }


    private Comment createTestComment() {
        return Comment.builder().boardId(1L).commenter("김세화").content("1빠").userId(1L).build();
    }
}
