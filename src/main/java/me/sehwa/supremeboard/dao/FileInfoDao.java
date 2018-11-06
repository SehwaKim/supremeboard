package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.FileInfo;
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
import java.util.List;
import java.util.Map;

public class FileInfoDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RowMapper<FileInfo> rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class);

    public FileInfoDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("file_info")
                .usingColumns("board_id", "name", "path", "size", "type")
                .usingGeneratedKeyColumns("id");
    }

    public Long insert(FileInfo fileInfo) throws RepositoryException {
        try {
            SqlParameterSource source = new BeanPropertySqlParameterSource(fileInfo);
            return jdbcInsert.executeAndReturnKey(source).longValue();
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public FileInfo selectOneById(Long id) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
            return jdbcTemplate.queryForObject(FileInfoSQL.selectOneById, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public List<FileInfo> selectAllByBoardId(Long boardId) throws RepositoryException {
        try {
            Map<String, Object> param = Collections.singletonMap("boardId", boardId);
            return jdbcTemplate.query(FileInfoSQL.selectAllByBoardId, param, rowMapper);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int updateDeleted(Long id) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("id", String.valueOf(id));
            return jdbcTemplate.update(FileInfoSQL.updateDeleted, param);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }
}
