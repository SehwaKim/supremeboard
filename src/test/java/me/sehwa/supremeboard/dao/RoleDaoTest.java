package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.config.DBConfig;
import me.sehwa.supremeboard.domain.Role;
import me.sehwa.supremeboard.domain.RoleName;
import me.sehwa.supremeboard.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DBConfig.class)
@Transactional
public class RoleDaoTest {

    @Autowired
    DataSource dataSource;
    RoleDao roleDao;
    UserDao userDao;

    @Before
    public void init() {
        roleDao = new RoleDao(dataSource);
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
        Long userId = userDao.insert(aUser);
        Role aRole = createTestRole();
        aRole.setRoleId(roleDao.selectRoleIdByName(RoleName.USER));
        aUser.setId(userId);
        aUser.addRole(aRole);
        // when
        int count = roleDao.insert(aRole);
        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void selectRoleIdByNameTest() {
        // when
        Long id = roleDao.selectRoleIdByName(RoleName.USER);
        // then
        assertThat(id).isNotNull();
    }

    @Test
    public void selectAllByUserIdTest() {
        // given
        User aUser = createTestUser();
        Long userId = userDao.insert(aUser);
        Role aRole = createTestRole();
        aRole.setRoleId(roleDao.selectRoleIdByName(RoleName.USER));
        aUser.setId(userId);
        aUser.addRole(aRole);
        roleDao.insert(aRole);
        // when
        List<Role> roles = roleDao.selectAllByUserId(userId);
        // then
        assertThat(roles).isNotEmpty();
    }

    private Role createTestRole() {
        return new Role(RoleName.USER);
    }

    private User createTestUser() {
        return new User("sehwakm@gmail.com", "1234", "Jane Kim");
    }
}
