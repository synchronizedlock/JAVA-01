import org.easley.spring.beans.Girl;
import org.easley.spring.beans.MoeGirl;
import org.easley.spring.proxy.cglib.CglibGirlProxyFactory;

/**
 * 测试Cglib动态代理
 *
 * @author Easley
 * @date 2021/2/20
 * @since 1.0
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        MoeGirl girl = new MoeGirl();
        girl.setName("Haruka");
        girl.setWeChat("haruka1996");

        // 找中间人（老鸨，不是）联系妹子
        Girl girlProxy = CglibGirlProxyFactory.getGirlProxy(girl);
        System.out.println("--------------------------");
        // 拿到妹子的名字和微信
        String name = girlProxy.getGirlName();
        String weChat = girlProxy.getWeChat();

        // 聊天刷好感度
        int intimacy = 0;
        for (int i = 0; i < 2; i++) {
            intimacy += girlProxy.chat(name, weChat, true);
        }

        // 求婚，老三无青年一年挣5块(梦里都有)
        if (girlProxy.makeAProposal(5, 0, 0, 0, intimacy)) {
            System.out.println(">>> 好耶!努力挣钱养家吧~");
        } else {
            System.out.println(">>> 考虑下男孩子也不是不行~");
        }
    }
}
