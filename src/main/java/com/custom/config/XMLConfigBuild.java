package com.custom.config;

import com.custom.io.Resources;
import com.custom.pojo.Configeration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuild {

    private Configeration configeration;

    public XMLConfigBuild() {
        this.configeration = new Configeration();
    }

    /**
     * 该方法就是使用dom4j对配置文件进行解析，封装Configuration
     *
     * @param inputStream
     * @return
     */
    public Configeration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        // <configuration>
        Element rootElement = document.getRootElement();
        // 获取所有property标签
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        // 创建链接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("dirverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("username"));

        // 对configuration中数据库信息进行封装
        configeration.setDataSource(comboPooledDataSource);

        // mapper.xml解析：拿到路径--字节输入流--dom4j进行解析
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configeration);
            xmlMapperBuilder.parse(resourceAsStream);
        }
        return configeration;
    }

}
