package cn.edu.nju.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * sql session factory
 * apply singleton design pattern
 */
public class SessionFactory {

    private static final SqlSessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(SessionFactory.class);

    static {
        SqlSessionFactory factory;
        String resource = "mybatisConfig.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            factory = null;
            logger.error("Fail to create session factory");
            logger.error(e);
            e.printStackTrace();
        }
        sessionFactory = factory;
    }

    private SessionFactory(){}

    public static SqlSessionFactory getInstance() {
        return sessionFactory;
    }
}
