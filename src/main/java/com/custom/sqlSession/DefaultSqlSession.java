package com.custom.sqlSession;

import com.custom.pojo.Configeration;
import com.custom.pojo.MappedStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.lang.reflect.*;

public class DefaultSqlSession implements SqlSession {

    private Configeration configeration;

    public DefaultSqlSession(Configeration configeration) {
        this.configeration = configeration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {
        //完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configeration.getMappedStatementMap().get(statementid);
        List<Object> list = simpleExecutor.query(configeration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid, params);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用jdk动态代理，为Dao接口生成代理对象并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            /**
             * 代理对象调用接口中的任意方法，都会执行invoke方法
             * @param proxy 当前代理对象的引用
             * @param method 当前被调用方法的引用
             * @param args 传递的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层还是执行JDBC代码，根据不同情况，调用selectList、selectOne
                // 准备参数1:statementid：sql语句的唯一标识:namespace.id=接口全限定名.方法名
                // 方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                // 准备参数2：params:args
                // 获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了 范型类型参数化
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }
                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }

}
