package cn.aaron911.generator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import cn.aaron911.generator.dao.GeneratorDao;
import cn.aaron911.generator.dao.MySQLGeneratorDao;
import cn.aaron911.generator.dao.OracleGeneratorDao;
import cn.aaron911.generator.dao.PostgreSQLGeneratorDao;
import cn.aaron911.generator.dao.SQLServerGeneratorDao;
import cn.aaron911.generator.utils.AException;

/**
 * 数据库配置
 *
 */
@Configuration
public class DbConfig {
	
    @Value("${aaron911.database: mysql}")
    private String database;
    
    @Autowired
    private MySQLGeneratorDao mySQLGeneratorDao;
    
    @Autowired
    private OracleGeneratorDao oracleGeneratorDao;
    
    @Autowired
    private SQLServerGeneratorDao sqlServerGeneratorDao;
    
    @Autowired
    private PostgreSQLGeneratorDao postgreSQLGeneratorDao;

    @Bean
    @Primary
    public GeneratorDao getGeneratorDao(){
        if("mysql".equalsIgnoreCase(database)){
            return mySQLGeneratorDao;
        }else if("oracle".equalsIgnoreCase(database)){
            return oracleGeneratorDao;
        }else if("sqlserver".equalsIgnoreCase(database)){
            return sqlServerGeneratorDao;
        }else if("postgresql".equalsIgnoreCase(database)){
            return postgreSQLGeneratorDao;
        }else {
            throw new AException("不支持当前数据库：" + database);
        }
    }
}
