package com.custom.sqlSession;

import com.custom.config.XMLConfigBuild;
import com.custom.pojo.Configeration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {
        // 1.使用dom4j解析配置文件，将解析出来的对象封装到Configuration中
        XMLConfigBuild xmlConfigBuild = new XMLConfigBuild();
        Configeration configeration = xmlConfigBuild.parseConfig(in);

        // 2.创建sqlSessionFactory对象：工厂类：生产sqlSession：会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configeration);

        return defaultSqlSessionFactory;
    }

}
