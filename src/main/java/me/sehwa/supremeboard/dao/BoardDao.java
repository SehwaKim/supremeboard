package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.Board;
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
public class BoardDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private SimpleJdbcInsert jdbcInsertForContent;
    private RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);

    public BoardDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("board").usingGeneratedKeyColumns("id");
        jdbcInsertForContent = new SimpleJdbcInsert(dataSource).withTableName("board_content").usingGeneratedKeyColumns("id");
    }

    public Long insert(Board aBoard) throws RepositoryException {
        try {
            SqlParameterSource source = new BeanPropertySqlParameterSource(aBoard);
            return jdbcInsert.executeAndReturnKey(source).longValue();
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public Board selectOneById(Long id) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
            return jdbcTemplate.queryForObject(BoardSQL.selectOneById, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public List<Board> selectAll(Pagination pagination, Long categoryId, String[] searchTypes, String searchStr) throws RepositoryException {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("categoryId", categoryId);
            map.put("searchStr", searchStr);
            map.put("startNum", pagination.getStartNum());
            map.put("postSize", pagination.getPostSize());
            String sql = BoardSQL.createSelectAllSQL(searchTypes);
            return jdbcTemplate.query(sql, map, rowMapper);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateFamily(Long id) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
            return jdbcTemplate.update(BoardSQL.updateFamily, param);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateTitle(Long id, String newTitle) throws RepositoryException {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", String.valueOf(id));
            param.put("title", newTitle);
            return jdbcTemplate.update(BoardSQL.updateTitle, param);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateHit(Long id) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
            return jdbcTemplate.update(BoardSQL.updateHit, param);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateCommentCnt(Long id) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
            return jdbcTemplate.update(BoardSQL.updateCommentCnt, param);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public Long insertContent(Board aBoard) throws RepositoryException {
        try {
            SqlParameterSource source = new BeanPropertySqlParameterSource(aBoard);
            return jdbcInsertForContent.executeAndReturnKey(source).longValue();
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateFamilySeq(Long family, int edgeOfFamilySeq) throws RepositoryException {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("family", String.valueOf(family));
            params.put("edgeOfFamilySeq", String.valueOf(edgeOfFamilySeq));
            return jdbcTemplate.update(BoardSQL.updateFamilySeq, params);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }
}
