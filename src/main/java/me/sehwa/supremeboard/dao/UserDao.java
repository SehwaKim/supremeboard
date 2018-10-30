package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.domain.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    public UserDao(DataSource dataSource) {

    }

    public Long insert(User aUser) {
        return null;
    }

    public User selectById(Long id) {
        return null;
    }

    public void deleteById(Long id) {
    }

    public void update(User aUser) {
    }
}
