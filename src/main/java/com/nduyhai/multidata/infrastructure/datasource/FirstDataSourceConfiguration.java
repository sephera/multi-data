package com.nduyhai.multidata.infrastructure.datasource;

import com.nduyhai.multidata.infrastructure.jpa.first.FirstEntity;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "firstEntityManager",
        transactionManagerRef = "firstTransactionManager",
        basePackageClasses = FirstEntity.class)
public class FirstDataSourceConfiguration extends AbtractDataSourceConfiguration {


    @Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;


    @Bean
    @ConfigurationProperties(prefix = "first.jpa")
    public JpaProperties firstProperties() {
        return new JpaProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "first.datasource")
    public DataSource firstDataSource() {
        return (DataSource) DataSourceBuilder.create().type(DataSource.class).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean firstEntityManager(
            JpaProperties firstProperties) {
        EntityManagerFactoryBuilder builder = this.createEntityManagerFactoryBuilder(firstProperties, persistenceUnitManager);
        return builder
                .dataSource(firstDataSource())
                .packages(FirstEntity.class)
                .persistenceUnit("firstDs")
                .build();
    }

    @Bean
    @Primary
    public JpaTransactionManager firstTransactionManager(EntityManagerFactory firstEntityManager) {
        return new JpaTransactionManager(firstEntityManager);
    }


}
