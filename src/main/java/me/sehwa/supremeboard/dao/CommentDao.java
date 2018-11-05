package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.Comment;
import me.sehwa.supremeboard.exception.RepositoryException;
import me.sehwa.supremeboard.util.Pagination;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);

    public CommentDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("comment").usingGeneratedKeyColumns("id");
    }

    public Long insert(Comment aComment) throws RepositoryException {
        try {
            SqlParameterSource source = new BeanPropertySqlParameterSource(aComment);
            return jdbcInsert.executeAndReturnKey(source).longValue();
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public Comment selectOneById(Long id) throws RepositoryException {
        try {
            Map<String, String> map = Collections.singletonMap("id", String.valueOf(id));
            return jdbcTemplate.queryForObject(CommentSQL.selectOneById, map, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public List<Comment> selectAll(Pagination pagination, Long boardId) throws RepositoryException {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("boardId", boardId);
//            map.put("startIdx", pagination.getStartIdx());
//            map.put("postSize", pagination.getPostSize());
            return jdbcTemplate.query(CommentSQL.selectAll, map, rowMapper);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateContent(String content, Long id) throws RepositoryException {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("content", content);
            map.put("id", String.valueOf(id));
            return jdbcTemplate.update(CommentSQL.updateContent, map);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }
}