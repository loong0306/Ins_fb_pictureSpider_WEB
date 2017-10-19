package me.dragon.base.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware {

    private Environment environment;
    private RelaxedPropertyResolver datasourcePropertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.datasourcePropertyResolver = new RelaxedPropertyResolver(environment,
                "spring.datasource.");
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        if (StringUtils.isEmpty(datasourcePropertyResolver.getProperty("url"))) {
            System.out.println("Your database connection pool configuration is incorrect!" +
                    " Please check your Spring profile, current profiles are:"+
                    Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException(
                    "Database connection pool is not configured correctly");
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(datasourcePropertyResolver.getProperty("url"));

        druidDataSource.setUsername(datasourcePropertyResolver
                .getProperty("username"));
        druidDataSource.setPassword(datasourcePropertyResolver
                .getProperty("password"));
        druidDataSource.setInitialSize(1);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 'x'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        return druidDataSource;
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() throws SQLException{
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(this.dataSource());
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.format_sql","true");
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.setPackagesToScan("me.dragon");
        System.out.println("LocalSessionFactoryBean = " + localSessionFactoryBean);
        return localSessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager txManager() throws SQLException {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
        return hibernateTransactionManager;
    }

}