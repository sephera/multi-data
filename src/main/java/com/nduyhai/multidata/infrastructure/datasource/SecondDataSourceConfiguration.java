package com.nduyhai.multidata.infrastructure.datasource;

import com.nduyhai.multidata.infrastructure.jpa.first.FirstEntity;
import com.nduyhai.multidata.infrastructure.jpa.second.SecondEntity;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManager",
        transactionManagerRef = "secondTransactionManager",
        basePackageClasses = SecondEntity.class)
public class SecondDataSourceConfiguration extends AbtractDataSourceConfiguration {

    @Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;


    @Bean
    @ConfigurationProperties(prefix = "second.jpa")
    public JpaProperties secondProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "second.datasource")
    public DataSource secondDataSource() {
        return (DataSource) DataSourceBuilder.create().type(DataSource.class).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondEntityManager(
            JpaProperties secondProperties) {
        EntityManagerFactoryBuilder builder = this.createEntityManagerFactoryBuilder(secondProperties, persistenceUnitManager);
        return builder
                .dataSource(secondDataSource())
                .packages(SecondEntity.class)
                .persistenceUnit("secondDs")
                .build();
    }

    @Bean
    public JpaTransactionManager secondTransactionManager(EntityManagerFactory secondEntityManager) {
        return new JpaTransactionManager(secondEntityManager);
    }
}
