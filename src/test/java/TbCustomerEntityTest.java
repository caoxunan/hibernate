import com.cxn.entity.TbCustomerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: hibernate
 * @description: CustomerEntity测试类
 * @author: cxn
 * @create: 2018-04-20 14:44
 * @Version v1.0
 */
public class TbCustomerEntityTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init(){
        // 创建配置项
        Configuration configuration = new Configuration().configure();
        // 创建会话工厂
        sessionFactory = configuration.buildSessionFactory();
        // 开启会话
        session = sessionFactory.openSession();
        // 开启事务
        transaction = session.beginTransaction();
    }

    @After
    public void destory(){
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testCustomer(){
        // 生成对象
        TbCustomerEntity customer = new TbCustomerEntity(2,"李四",25,"shanghai");
        // 保存对象
        session.save(customer);
    }

}
