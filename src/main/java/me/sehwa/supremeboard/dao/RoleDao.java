package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.Role;
import me.sehwa.supremeboard.domain.RoleName;
import me.sehwa.supremeboard.exception.RepositoryException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class);

    public RoleDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("user_role");
    }

    public int insert(Role aRole) throws RepositoryException {
        try {
            SqlParameterSource source = new BeanPropertySqlParameterSource(aRole);
            return jdbcInsert.execute(source);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public Long selectRoleIdByName(RoleName name) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("name", name.name());
            return jdbcTemplate.queryForObject(RoleSQL.selectRoleIdByName, param, Long.class);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }

    public List<Role> selectAllByUserId(Long userId) throws RepositoryException {
        try {
            Map<String, String> param = Collections.singletonMap("userId", String.valueOf(userId));
            return jdbcTemplate.query(RoleSQL.selectAllByUserId, param, rowMapper);
        } catch (RuntimeException e) {
            throw new RepositoryException(e);
        }
    }
}
