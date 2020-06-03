package com.custom.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configeration {

    private DataSource dataSource;

    /**
     * key:statementId
     * value:封装好的MappedStatement对象
     */
    Map<String,MappedStatement> mappedStatementMap = new HashMap<String, MappedStatement>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
