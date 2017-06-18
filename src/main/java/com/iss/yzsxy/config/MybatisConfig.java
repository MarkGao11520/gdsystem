package com.iss.yzsxy.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by gaowenfeng on 2017/2/5.
 * mybatis配置类
 */

@Configuration
@EnableTransactionManagement
@MapperScan("com.iss.yzsxy.dao")
public class MybatisConfig implements TransactionManagementConfigurer {
    @Autowired
    DataSource dataSource;

//    @Bean(destroyMethod = "close")
//    public DruidDataSource dataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/gwf?useUnicode=true&characterEncoding=utf-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("zrkj123");
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        //配置最大连接
//        dataSource.setMaxActive(20);
//        //配置初始连接
//        dataSource.setInitialSize(1);
//        //配置最小连接
//        dataSource.setMinIdle(1);
//        //连接等待超时时间
//        dataSource.setMaxWait(60000);
//        //间隔多久进行检测,关闭空闲连接
//        dataSource.setTimeBetweenEvictionRunsMillis(60000);
//        //一个连接最小生存时间
//        dataSource.setMinEvictableIdleTimeMillis(300000);
//        //用来检测是否有效的sql
//        dataSource.setValidationQuery("select 'x'");
//        dataSource.setTestWhileIdle(true);
//        dataSource.setTestOnBorrow(false);
//        dataSource.setTestOnReturn(false);
//        //打开PSCache,并指定每个连接的PSCache大小
//        dataSource.setPoolPreparedStatements(true);
//        dataSource.setMaxOpenPreparedStatements(20);
//        //配置sql监控的filter
//        try {
//            dataSource.setFilters("stat,wall,log4j");
//            dataSource.init();
//        } catch (SQLException e) {
//            throw new RuntimeException("druid datasource init fail");
//        }
//        return dataSource;
//    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        Interceptor[] plugins = new Interceptor[]{pageHelper()};
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPlugins(plugins);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //设置xml扫描路径
            bean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("sqlSessionFactory init fail",e);
        }
    }

    @Bean
    public PageHelper pageHelper(){
        return new PageHelper();
    }



    /**
     * 事务管理,具体使用在service层加入@Transactional注解
     */
    @Bean(name = "transactionManager")
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
