import com.cxn.entity.TbCustomerEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

    /**
     * 保存操作
     * session.save(Object);
     */
    @Test
    public void testAddCustomer(){
        // 生成对象
        // TbCustomerEntity customer = new TbCustomerEntity(2,"lisi",25,"shanghai");
        // 保存对象
        // session.save(customer);

        TbCustomerEntity customer1 = new TbCustomerEntity();
        customer1.setAge(18);
        customer1.setName("lisi");
        customer1.setCity("beijing");
        session.save(customer1);
    }


    /**
     * 更新操作，必须要有id 根据id进行更新
     * session.update(Object);
     */
    @Test
    public void testUpdateCustomer(){
        TbCustomerEntity customer = new TbCustomerEntity();
        customer.setId(1);
        customer.setAge(30);
        customer.setName("Lucy");
        customer.setCity("朝阳区");
        session.update(customer);
    }

    /**
     * 删除操作
     * session.delete(Object);
     */
    @Test
    public void testDeleteCustomer(){
        TbCustomerEntity customer = new TbCustomerEntity();
        customer.setId(5);
        session.delete(customer);
    }

    /**
     * 根据主键查询数据
     * session.get(Xxx.class, id);
     * session.load(Xxx.class, id);
     */
    @Test
    public void testQueryById(){
        TbCustomerEntity customerEntity1 = (TbCustomerEntity) session.get(TbCustomerEntity.class, 3);
        System.out.println("customerEntity1:" + customerEntity1);
        TbCustomerEntity customerEntity2 = (TbCustomerEntity) session.load(TbCustomerEntity.class, 2);
        System.out.println("customerEntity2:" + customerEntity2);
    }



}
