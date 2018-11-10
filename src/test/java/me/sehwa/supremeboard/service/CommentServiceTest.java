package me.sehwa.supremeboard.service;

import me.sehwa.supremeboard.config.RootContextConfig;
import me.sehwa.supremeboard.domain.Comment;
import me.sehwa.supremeboard.util.Pagination;
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
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Test
    public void addCommentTest() {
        // given
        int commentCntBefore = commentService.getCommentsByBoard(new Pagination(), 1L).size();
        Comment aComment = Comment.builder()
                        .userId(1L)
                        .content("hahaha")
                        .commenter("sehwa")
                        .boardId(1L)
                        .build();
        // when
        Long id = commentService.addComment(aComment);
        // then
        assertThat(id).isNotNull();
        int commentCntAfter = commentService.getCommentsByBoard(new Pagination(), 1L).size();
        assertThat(commentCntAfter).isEqualTo(commentCntBefore + 1);
    }
}
