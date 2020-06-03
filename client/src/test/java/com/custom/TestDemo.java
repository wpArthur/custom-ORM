package com.custom;

import com.custom.dao.IUserDao;
import com.custom.io.Resources;
import com.custom.pojo.User;
import com.custom.sqlSession.SqlSession;
import com.custom.sqlSession.SqlSessionFactory;
import com.custom.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class TestDemo {

    @Test
    public void Test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 调用
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
//        User user1 = sqlSession.selectOne("user.selectOne", user);
//        System.out.println(user1);
//
//        List<Object> user2 = sqlSession.selectList("user.selectList");
//        System.out.println(user2);
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        User user1 = userDao.fingByCondition(user);
        System.out.println(user1.toString());

        List<User> users = userDao.findAll();
        System.out.println(users.toString());
    }
}