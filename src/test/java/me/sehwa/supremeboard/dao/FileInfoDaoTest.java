package me.sehwa.supremeboard.dao;

import me.sehwa.supremeboard.config.DBConfig;
import me.sehwa.supremeboard.domain.Comment;
import me.sehwa.supremeboard.domain.FileInfo;
import me.sehwa.supremeboard.exception.RepositoryException;
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
public class FileInfoDaoTest {

    @Autowired
    DataSource dataSource;
    FileInfoDao fileInfoDao;

    @Before
    public void init() {
        fileInfoDao = new FileInfoDao(dataSource);
    }

    @Test
    public void dataSourceTest() {
        assertThat(dataSource).isNotNull();
    }

    @Test
    public void insertTest() {
        // given
        FileInfo aFile = createTestFileInfo();
        // when
        Long id = fileInfoDao.insert(aFile);
        // then
        assertThat(id).isNotNull();
        FileInfo theFile = fileInfoDao.selectOneById(id);
        assertThat(theFile).isNotNull();
        assertThat(theFile.getBoardId()).isEqualTo(aFile.getBoardId());
        assertThat(theFile.getPath()).isEqualTo(aFile.getPath());
        assertThat(theFile.getId()).isEqualTo(id);
    }

    @Test
    public void selectOneByIdTest() {
        // given
        Long id = fileInfoDao.insert(createTestFileInfo());
        // when
        FileInfo theFile = fileInfoDao.selectOneById(id);
        // then
        assertThat(theFile.getId()).isEqualTo(id);
    }

    @Test
    public void selectAllByBoardIdTest() {
        // when
        List<FileInfo> list = fileInfoDao.selectAllByBoardId(100L);
        // then
        assertThat(list.size()).isEqualTo(0);

        // given
        Long id = fileInfoDao.insert(createTestFileInfo());
        // when
        list = fileInfoDao.selectAllByBoardId(1L);
        // then
        assertThat(list).isNotEmpty();
    }

    @Test
    public void updateDeletedTest() {
        // given
        Long id = fileInfoDao.insert(createTestFileInfo());
        // when
        int count = fileInfoDao.updateDeleted(id);
        // then
        assertThat(count).isEqualTo(1);
        FileInfo theFile = fileInfoDao.selectOneById(id);
        assertThat(theFile).isNull();
    }

    private FileInfo createTestFileInfo() {
        return FileInfo.builder().boardId(1L).name("boy.jpg").path("/image/boy.jpg").size(500L).type("image/jpg").build();
    }
}
