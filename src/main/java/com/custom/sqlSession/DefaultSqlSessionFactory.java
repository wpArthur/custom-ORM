package com.custom.sqlSession;

import com.custom.pojo.Configeration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configeration configeration;

    public DefaultSqlSessionFactory(Configeration configeration) {
        this.configeration = configeration;
    }


    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configeration);
    }
}
