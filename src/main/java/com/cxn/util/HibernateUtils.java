package com.cxn.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @program: hibernate
 * @description: get SessionFactory or Session by this util
 * @author: cxn
 * @create: 2018-04-20 15:46
 * @Version v1.0
 */
public class HibernateUtils {

    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        // JVM停止运行的时候：为JVM增加一个监听事件，在jvm停止工作的时候，关闭sessionFactory
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                sessionFactory.close();
                System.out.println("sessionFactory is close.");
            }
        });
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static Session openSession(){
        return sessionFactory.openSession();
    }

}
