package com.nduyhai.multidata.infrastructure.datasource;


import org.apache.tomcat.jdbc.pool.jmx.ConnectionPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = Constrant.Second.ENTITY_MANAGER,
        transactionManagerRef = Constrant.Second.TRANSACTION_MANAGER,
        basePackages = {Constrant.Second.REPO_PACKAGE})
public class SecondDataSource extends AbstractMultipleDataSource {

    @Bean(name = Constrant.Second.PROPERTIES)
    @ConfigurationProperties(prefix = Constrant.Second.PREFIX_PROPERTIES)
    public JpaProperties properties() {
        return new JpaProperties();
    }

    @Bean(name = Constrant.Second.DATASOURCE)
    @ConfigurationProperties(prefix = Constrant.Second.PREFIX_DATASOURCE)
    public DataSource dataSource() {
        return (DataSource) DataSourceBuilder.create().type(DataSource.class).build();
    }

    @Bean(name = Constrant.Second.POOL)
    public ConnectionPool jmxPool(@Qualifier(Constrant.Second.DATASOURCE) DataSource dataSource) throws SQLException {
        return this.jmxPool(dataSource);
    }

    @Bean(name = Constrant.Second.ENTITY_MANAGER)
    public LocalContainerEntityManagerFactoryBean entityManager(
            @Qualifier(Constrant.Second.PROPERTIES) JpaProperties properties,
            @Qualifier(Constrant.Second.DATASOURCE) DataSource datasource) {
        return this.buildEntityManager(properties, datasource);
    }


    @Bean(name = Constrant.Second.TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(@Qualifier(Constrant.Second.ENTITY_MANAGER) LocalContainerEntityManagerFactoryBean entityManager) {
        return this.buildTransactionManager(entityManager);
    }


    @Override
    protected String entityPackage() {
        return Constrant.Second.ENTITY_PACKAGE;
    }

    @Override
    protected String unitName() {
        return Constrant.Second.UNIT;
    }
}
