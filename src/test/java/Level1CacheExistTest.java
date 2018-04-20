import com.cxn.entity.TbCustomerEntity;
import com.cxn.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: hibernate
 * @description: 证明一级缓存的存在性
 * @author: cxn
 * @create: 2018-04-20 17:01
 * @Version v1.0
 */
public class Level1CacheExistTest {

    private Session session = HibernateUtils.openSession();



    /**
     * 第一次session.get()查询后对象变为持久态，保存在一级缓存中
     * 第二次session.get()的时候，命中缓存，直接拿
     */
    @Test
    public void testQueryById(){
        /**
         * 证明：一级缓存的生命周期：与session同生命共存亡
         * 证明思路：言外之意，就是不能跨session共享数据
         * 证明步骤：
         * 1 开启session1，查询数据
         * 2 证明session1中已经存在了数据（获取这条数据的时候，不会再发sql语句）
         * 3 关闭session1
         * 4 开启session2，查询数据，观察是否发出sql语句，如果发，表明数据随着session1的一起销毁了
         * 5 关闭session2
         */
        /**************第一次查询**************/
        session.beginTransaction();
        TbCustomerEntity customerEntity1 = (TbCustomerEntity) session.get(TbCustomerEntity.class, 3);
        session.getTransaction().commit();
        TbCustomerEntity customerEntity2 = (TbCustomerEntity) session.get(TbCustomerEntity.class, 3);
        System.out.println("customerEntity1:" + customerEntity1);
        System.out.println("customerEntity2:" + customerEntity1);
        // 关闭session
        session.getTransaction().commit();
        session.close();

        /**************第二次查询**************/
        Session session2 = HibernateUtils.openSession();
        session2.beginTransaction();
        TbCustomerEntity customerEntity3 = (TbCustomerEntity) session2.get(TbCustomerEntity.class, 3);
        session2.getTransaction().commit();
        System.out.println("customerEntity3:" + customerEntity2);
    }

}
