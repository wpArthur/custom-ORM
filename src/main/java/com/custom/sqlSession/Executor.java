package com.custom.sqlSession;

import com.custom.pojo.Configeration;
import com.custom.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    public <E> List<E> query(Configeration configeration, MappedStatement mappedStatement, Object... params) throws Exception;

}
