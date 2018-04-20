import com.cxn.entity.TbCustomerEntity;
import com.cxn.util.HibernateUtils;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @program: hibernate
 * @description: Query查询
 * @author: cxn
 * @create: 2018-04-20 15:53
 * @Version v1.0
 */
public class QueryEntityTest {

    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private Session session = HibernateUtils.openSession();
    private Transaction transaction;

    @Before
    public void init() {
        transaction = session.beginTransaction();
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }


    /**
     * 各种查询
     * HQL
     * SQL
     */
    @Test
    public void testQuery() {
        // 1 使用HQL语句
        // HQL：以面向对象的方式操作数据库，所以里面填写的要么是对象，要么是对象的属性
        /******  HQL进行条查询全部  *******/

        //方式一
        // List<TbCustomerEntity> list = session.createQuery("from TbCustomerEntity").list();

        //方式二
        // Query query = session.createQuery("select c from TbCustomerEntity c");
        // List<TbCustomerEntity> list = query.list();

        //方式三：错误的方式，HQL不支持*
        //	List<TbCustomerEntity> list = session.createQuery("select * from TbCustomerEntity").list();

        //for (TbCustomerEntity customerEntity : list) {
        //   System.out.println(customerEntity);
        //}


        // 2 使用SQL语句
        // SQL:由于sqlQuery返回的是Object[] 所以需要用addEntity指定封装的实体
        //SQLQuery sqlQuery = session.createSQLQuery("select * from tb_customer");
        //List<TbCustomerEntity> sqlList = sqlQuery.addEntity(TbCustomerEntity.class).list();
        //for (TbCustomerEntity customerEntity : sqlList) {
        //    System.out.println(customerEntity);
        //}
    }

    /**
     * HQL条件查询
     */
    @Test
    public void testHQLConditionQuery() {

        /******  HQL进行条件查询  *******/
        // 查询名字为'李四'的用户的所有信息
        // 方式一:条件写死，几乎不用
        // TbCustomerEntity customer = (TbCustomerEntity) session.createQuery("from TbCustomerEntity where name = '李四'").uniqueResult();
        // 方式二：匿名的方式注入参数
        // TbCustomerEntity customer = (TbCustomerEntity)  session.createQuery("from TbCustomerEntity where name = ?")
        //      .setString(0, "李四")//hibernate中的计数从0开始
        //      .uniqueResult();

        // 方式三：匿名的方式注入参数
        // TbCustomerEntity customer = (TbCustomerEntity) session.createQuery("from TbCustomerEntity where name = ?").setParameter(0, "李四").uniqueResult();

        // 方式四：命名的方式注入参数
        // TbCustomerEntity customer = (TbCustomerEntity)  session.createQuery("from TbCustomerEntity where name = :name").setString("name", "李四").uniqueResult();

        // 方式五：命名的方式注入参数
        TbCustomerEntity customer = (TbCustomerEntity) session.createQuery("from TbCustomerEntity where name = :name").setParameter("name", "李四").uniqueResult();

        System.out.println("HQL进行条件查询:" + customer);

    }

    /**
     * SQL条件查询
     */
    @Test
    public void testSQLConditionQuery() {
        /**********根据条件查询：查询名字为'李四' 的客户的所有信息************/
        // 方式一
        // TbCustomerEntity customer = (TbCustomerEntity)  session.createSQLQuery("select * from tb_customer where name = '李四'").addEntity(TbCustomerEntity.class).uniqueResult();

        // 方式二：匿名方式
        // TbCustomerEntity customer = (TbCustomerEntity) session.createSQLQuery("select * from tb_customer where name = ?")
        //        // 注意要先添加实体在注入参数
        //        .addEntity(TbCustomerEntity.class)
        //        // .setString(0, "李四")
        //        .setParameter(0,"李四")
        //        .uniqueResult();

        // 方式三：命名的方式
        TbCustomerEntity customer = (TbCustomerEntity) session.createSQLQuery("select * from tb_customer where name = :name")
                .addEntity(TbCustomerEntity.class)
                .setParameter("name", "李四")
                .uniqueResult();

        System.out.println(customer);
    }

    @Test
    public void testCriteriaQuery(){
        // 创建criteria
        Criteria criteria = session.createCriteria(TbCustomerEntity.class);
        // 查询所有
        List<TbCustomerEntity> list = criteria.list();
        for (TbCustomerEntity customerEntity : list) {
            System.out.println(customerEntity);
        }

        // 根据条件查询
        Criteria criteria2 = session.createCriteria(TbCustomerEntity.class);
        // 添加条件
        criteria2.add(Restrictions.eq("name", "李四"));
        // 继续添加条件
        criteria2.add(Restrictions.lt("age", 20));
        TbCustomerEntity customerEntity = (TbCustomerEntity) criteria2.uniqueResult();
        System.out.println(customerEntity);


    }

}
