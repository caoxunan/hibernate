import com.cxn.entity.TbCustomerEntity;
import com.cxn.util.HibernateUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.junit.Test;

/**
 * @program: hibernate
 * @description: 测试快照自动更新的效果
 * @author: cxn
 * @create: 2018-04-20 17:17
 * @Version v1.0
 */
public class SnapShotTest {

    @Test
    public void testSnapShot(){
        /**
         * 证明：一级缓存的快照可以自动发 update语句，更新数据库
         * 证明思路：
         * 1 查询数据，将数据放入一级缓存和一级缓存的快照中
         * 2 修改一级缓存中的数据
         * 3 进行flush操作，看是否发出update语句
         * 优点：快照的自动更新可以用来更新部分数据区别于update语句，更适合使用
         */
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //此时，数据放入了一级缓存，也放入了快照
        TbCustomerEntity customer = (TbCustomerEntity) session.get(TbCustomerEntity.class, 2);
        //2 改
        if (customer != null) {
            customer.setName("lisi");
        }
        // 手动刷出缓存
        // session.flush();

        // 3 在commit的时候，系统会先隐式的flush，然后在提交数据
        session.getTransaction().commit();
        session.close();

    }

    @Test
    public void testGetAndLoadCache(){
        /**
         * 证明：get和load的工作方式：即使一级缓存和快照的数据不一致，也直接从一级缓存获取数据
         * 证明步骤：1 查数据
         *        2 改数据
         *        3 再查数据，观察是否发sql语句
         */
        Session session = HibernateUtils.openSession();
        session.beginTransaction();

        // 1 查数据
        TbCustomerEntity customerEntity = (TbCustomerEntity) session.get(TbCustomerEntity.class, 2);
        System.out.println(customerEntity);
        // 2 update data
        customerEntity.setName("jack");
        // 3 query again 不发sql语句，直接从一级缓存中取出数据
        TbCustomerEntity customerEntity2 = (TbCustomerEntity) session.get(TbCustomerEntity.class, 2);
        System.out.println(customerEntity2);
    }

    @Test
    public void testQueryCache(){
        /**
         * 证明：即使一级缓存有数据，query也不走一级缓存，也是直接从数据库查询数据
         * 证明步骤：
         * 1 查询数据，将数据放入一级缓存和快照
         * 2 证明一级缓存有数据
         * 3 通过query查询，发现发出sql语句，表明queyr不走一级缓存
         */
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        // 1 必发sql语句，并且将数据放入一级缓存和快照
        TbCustomerEntity customer1 = (TbCustomerEntity) session.get(TbCustomerEntity.class, 2);
        System.out.println(customer1);
        // 2 证明一级缓存有id=1的数据
        //此时不发sql语句，证明缓存中有数据
        TbCustomerEntity customer2 = (TbCustomerEntity) session.get(TbCustomerEntity.class, 2);
        System.out.println(customer2);

        // 3 即使缓存中有数据，query也是直接发sql语句查询数据库
        //此时必发sql语句
        TbCustomerEntity customer3 = (TbCustomerEntity) session.createQuery("from TbCustomerEntity where id = 2").uniqueResult();
        System.out.println(customer3);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testQueryCache2(){
        /**
         * 证明：query的时候，会去比较一级缓存和快照是否一致，如果不一致，会先发update 语句，再发select 语句
         * 证明步骤：
         * 1 将数据放入一级缓存和快照
         * 2 修改数据
         * 3 query查询，观察控制台发出的sql语句，一条update+一条select
         */
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        // 1 查询数据，放入一级缓存和快照
        TbCustomerEntity customer1 = (TbCustomerEntity) session.get(TbCustomerEntity.class, 2);
        System.out.println(customer1);

        // 2 修改一级缓存的数据
        customer1.setName("lianglong");
        // 3 query查询,观察控制台的sql语句
        TbCustomerEntity customer3 = (TbCustomerEntity) session.createQuery("from TbCustomerEntity where id = 2").uniqueResult();
        System.out.println(customer3);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testFlushMode(){
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        // 1 第一步证明：先不设置FlushMode，看运行结果
        TbCustomerEntity customer = (TbCustomerEntity)session.get(TbCustomerEntity.class, 1);
        //改变缓存中对象的属性
        customer.setName("lucy");

        // 2 第二证明：设置FlushMode，看运行结果
        //这个一旦设置，只有手动flush的时候，才会发出update语句
        session.setFlushMode(FlushMode.MANUAL);
        //第二种方式，手动flush，发出update语句
        session.flush();

        //在第一种情况下，此处必然发出update语句
        //在第二种情况下，此处必然不会发出update语句
        session.getTransaction().commit();
        session.close();

    }

}
