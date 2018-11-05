package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.Board;
import me.sehwa.supremeboard.exception.RepositoryException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);

    public BoardDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("board").usingGeneratedKeyColumns("id");
    }

    public Long insert(Board aBoard) throws RepositoryException {
        SqlParameterSource source = new BeanPropertySqlParameterSource(aBoard);
        try {
            Number id = jdbcInsert.executeAndReturnKey(source);
            return id.longValue();
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public Board selectById(Long id) throws RepositoryException {
        Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
        try {
            return jdbcTemplate.queryForObject(BoardSQL.selectOneById, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateTitle(Long id, String newTitle) throws RepositoryException {
        Map<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        param.put("title", newTitle);
        try {
            return jdbcTemplate.update(BoardSQL.updateTitle, param);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateHit(Long id) {
        Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
        try {
            return jdbcTemplate.update(BoardSQL.updateHit, param);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateCommentCnt(Long id) {
        Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
        try {
            return jdbcTemplate.update(BoardSQL.updateCommentCnt, param);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }
}
