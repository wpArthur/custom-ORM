package com.custom.dao;

import com.custom.pojo.User;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.util.List;

public interface IUserDao {

    // 查询所用用户
    public List<User> findAll() throws Exception;

    // 根据条件查询用户
    public  User fingByCondition(User user) throws Exception;

}
