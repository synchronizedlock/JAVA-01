import org.easley.spring.beans.IdolGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring Bean装配测试
 *
 * @author Easley
 * @date 2021/2/20
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringBeanWireTest {
    /**
     * XML装配，其中有XML引用JavaConfig配置的Bean
     */
    @Autowired
    private IdolGroup gamingGirls;

    @Test
    public void testBeanAutowire() {
        System.out.println("----------------------------");
        // 测试切面
        gamingGirls.getProducer().selfIntroduction();
    }
}
