import org.easley.demo.JdbcDemoBootstrap;
import org.easley.demo.entity.Girl;
import org.easley.demo.service.GirlService;
import org.easley.demo.utils.JdbcUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JdbcDemoBootstrap.class)
public class JdbcTest {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private GirlService girlService;

    @Test
    public void testJdbcUtils() {
        Connection connection = JdbcUtils.connect(Driver.class.getCanonicalName()
                , dataSourceProperties.getUrl()
                , dataSourceProperties.getUsername()
                , dataSourceProperties.getPassword());

        JdbcUtils.Command insertCommand = JdbcUtils.buildCommand("insert into girl(address, name, phone) values(?, ?, ?)",  "千叶县", "Haruka", "123456");
        JdbcUtils.Command updateCommand = JdbcUtils.buildCommand("update girl set phone = ? where id = ?", "666666", 8);
        JdbcUtils.Command deleteCommand = JdbcUtils.buildCommand("delete from girl where name = ?").params("Haruka");
        JdbcUtils.Command selectCommand = JdbcUtils.buildCommand("select * from girl");

        // 增
        System.out.println(JdbcUtils.update(connection, insertCommand));
        // 改
        System.out.println(JdbcUtils.update(connection, updateCommand));
        // 删
        System.out.println(JdbcUtils.update(connection, deleteCommand));
        // 查
        JdbcUtils.query(connection, selectCommand, (row, rowNumber) -> System.out.println(String.format("row: %s, data: %s", rowNumber, row)));

        // 事务
        JdbcUtils.transaction(connection, insertCommand, updateCommand, deleteCommand);

        JdbcUtils.disconnect(connection);
    }

    @Test
    public void testHikariCP() {
        // 使用HikariCP + MyBatisPlus，就不赘述了
        List<Girl> girls = girlService.list();
        System.out.println(girls);
    }
}
