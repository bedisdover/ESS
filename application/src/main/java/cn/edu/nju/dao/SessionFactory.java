package cn.edu.nju.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * sql session factory
 * apply singleton design pattern
 */
public class SessionFactory {

    private static final SqlSessionFactory sessionFactory;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );
        sessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
    }

    private SessionFactory(){}

    public static SqlSessionFactory getInstance() {
        return sessionFactory;
    }
}
