import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.aaron911.api.dao.UserDao;
import cn.aaron911.api.entity.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleTest.class)
@MapperScan("cn.aaron911.api.dao.*")
public class SampleTest {

    @Autowired
    private UserDao userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<UserEntity> userList = userMapper.selectList(null);
       
        userList.forEach(System.out::println);
    }

}