package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.User;
import me.sehwa.supremeboard.exception.DaoLayerException;
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

    public Long insert(User aUser) throws DaoLayerException {
        SqlParameterSource source = new BeanPropertySqlParameterSource(aUser);
        Number id = jdbcInsert.executeAndReturnKey(source);
        return id.longValue();
    }

    public User selectById(Long id) throws DaoLayerException {
        Map<String, String> param = Collections.singletonMap("id", String.valueOf(id)); // named parameter 에 Long 안된다.
        try {
            return jdbcTemplate.queryForObject(UserSQL.selectOneById, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User selectByEmail(String email) throws DaoLayerException {
        Map<String, String> param = Collections.singletonMap("email", String.valueOf(email));
        try {
            return jdbcTemplate.queryForObject(UserSQL.selectOneByEmail, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int update(User aUser) throws DaoLayerException {
        SqlParameterSource params = new BeanPropertySqlParameterSource(aUser);
        return jdbcTemplate.update(UserSQL.update, params);
    }
}
