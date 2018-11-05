package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.User;
import me.sehwa.supremeboard.exception.RepositoryException;
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
import java.util.Map;

@Repository
public class UserDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("user").usingGeneratedKeyColumns("id");
    }

    public Long insert(User aUser) throws RepositoryException {
        try {
            SqlParameterSource source = new BeanPropertySqlParameterSource(aUser);
            return jdbcInsert.executeAndReturnKey(source).longValue();
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public User selectById(Long id) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("id", String.valueOf(id)); // named parameter 에 Long 안된다.
            return jdbcTemplate.queryForObject(UserSQL.selectOneById, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public User selectByEmail(String email) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("email", String.valueOf(email));
            return jdbcTemplate.queryForObject(UserSQL.selectOneByEmail, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public int update(User aUser) throws RepositoryException {
        try {
            SqlParameterSource params = new BeanPropertySqlParameterSource(aUser);
            return jdbcTemplate.update(UserSQL.update, params);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }
}
