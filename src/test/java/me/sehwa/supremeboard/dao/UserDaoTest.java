package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.config.DBConfig;
import me.sehwa.supremeboard.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DBConfig.class)
public class UserDaoTest {

    @Autowired
    DataSource dataSource;
    UserDao userDao;

    @Before
    public void init() {
        userDao = new UserDao(dataSource);
    }

    @Test
    public void dataSourceTest() {
        assertThat(dataSource).isNotNull();
    }

    @Test
    public void insertTest() {
        // given
        User aUser = createTestUser();
        // when
        Long id = userDao.insert(aUser);
        User theUser = userDao.selectById(id);
        // then
        assertThat(theUser.getEmail()).isEqualTo(aUser.getEmail());
        assertThat(theUser.getPassword()).isEqualTo(aUser.getPassword());
        assertThat(theUser.getName()).isEqualTo(aUser.getName());
    }

    @Test
    public void selectByIdTest() {
        // given
        User aUser = createTestUser();
        // when
        Long id = userDao.insert(aUser);
        // then
        assertThat(userDao.selectById(id)).isNotNull();

        // when
        userDao.deleteById(id);
        // then
        assertThat(userDao.selectById(id)).isNull();
    }

    @Test
    public void updateTest() {
        // given
        Long id = userDao.insert(createTestUser());
        User theUser = userDao.selectById(id);

        // when
        theUser.setName("user5049");
        theUser.setEmail("user5049@gmail.com");
        userDao.update(theUser);

        // then
        User updatedUser = userDao.selectById(id);
        assertThat(theUser.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(theUser.getPassword()).isEqualTo(updatedUser.getPassword());
        assertThat(theUser.getName()).isEqualTo(updatedUser.getName());
    }

    private User createTestUser() {
        return new User("sehwakm@gmail.com", "1234", "admin");
    }
}
