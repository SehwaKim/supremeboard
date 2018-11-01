package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.config.DBConfig;
import me.sehwa.supremeboard.domain.User;
import me.sehwa.supremeboard.domain.UserStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DBConfig.class)
@Transactional // 롤백하라고 안해도 테스트메소드는 자동으로 롤백시켜주네...
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
        assertThat(theUser.getStatus()).isEqualTo(UserStatus.ENABLED.name());
        assertThat(theUser.getEmail()).isEqualTo(aUser.getEmail());
        assertThat(theUser.getName()).isEqualTo(aUser.getName());
    }

    @Test
    public void selectByIdTest() {
        // given
        User aUser = createTestUser();
        Long id = userDao.insert(aUser);
        // when
        User theUser = userDao.selectById(id);
        // then
        assertThat(theUser).isNotNull();

        // when
        theUser.setStatus(UserStatus.DISABLED);
        int count = userDao.update(theUser);
        // then
        assertThat(count).isEqualTo(1);
        assertThat(userDao.selectById(id)).isNull();
    }

    @Test
    public void selectByEmailTest() {
        // given
        User aUser = createTestUser();
        userDao.insert(aUser);
        // when
        User theUser = userDao.selectByEmail(aUser.getEmail());
        // then
        assertThat(theUser).isNotNull();

        // when
        theUser.setStatus(UserStatus.DISABLED);
        int count = userDao.update(theUser);
        // then
        assertThat(count).isEqualTo(1);
        assertThat(userDao.selectByEmail(theUser.getEmail())).isNull();
    }

    @Test
    public void updateStatusTest() {
        // given
        Long id = userDao.insert(createTestUser());
        // when
        User theUser = userDao.selectById(id);
        // then
        assertThat(theUser.getStatus()).isEqualTo(UserStatus.ENABLED.name());

        // when
        theUser.setStatus(UserStatus.DISABLED);
        int count = userDao.update(theUser);
        User updatedUser = userDao.selectById(id);
        // then
        assertThat(count).isEqualTo(1);
        assertThat(updatedUser).isNull();
    }

    @Test
    public void updateTest() {
        // given
        Long id = userDao.insert(createTestUser());
        User theUser = userDao.selectById(id);

        // when
        theUser.setEmail("user5049@gmail.com");
        theUser.setPassword("4321");
        theUser.setName("user5049");
        int count = userDao.update(theUser);

        // then
        assertThat(count).isEqualTo(1);
        User updatedUser = userDao.selectById(id);
        assertThat(theUser.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(theUser.getPassword()).isEqualTo(updatedUser.getPassword());
        assertThat(theUser.getName()).isEqualTo(updatedUser.getName());
    }

    private User createTestUser() {
        return new User("sehwakm@gmail.com", "1234", "admin");
    }
}
