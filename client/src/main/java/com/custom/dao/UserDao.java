package com.custom.dao;

import com.custom.io.Resources;
import com.custom.pojo.User;
import com.custom.sqlSession.SqlSession;
import com.custom.sqlSession.SqlSessionFactory;
import com.custom.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class UserDao implements IUserDao {

    public List<User> findAll() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        return sqlSession.selectList("user.selectList");
    }

    public User fingByCondition(User user) throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        return sqlSession.selectOne("user.selectOne", user);
    }
}
