package com.nduyhai.multidata.infrastructure.datasource;


import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.DATASOURCE;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.ENTITY_MANAGER;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.ENTITY_PACKAGE;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.POOL;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.PREFIX_DATASOURCE;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.PREFIX_PROPERTIES;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.PROPERTIES;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.REPO_PACKAGE;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.TRANSACTION_MANAGER;
import static com.nduyhai.multidata.infrastructure.datasource.DataSourceConstant.Second.UNIT;

import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = ENTITY_MANAGER,
    transactionManagerRef = TRANSACTION_MANAGER,
    basePackages = {REPO_PACKAGE})
public class SecondDataSourceConfiguration extends AbstractMultipleDataSource {

  @Bean(name = PROPERTIES)
  @ConfigurationProperties(prefix = PREFIX_PROPERTIES)
  public JpaProperties properties() {
    return new JpaProperties();
  }

  @Bean(name = DATASOURCE)
  @ConfigurationProperties(prefix = PREFIX_DATASOURCE)
  public DataSource dataSource() {
    return DataSourceBuilder.create().type(DataSource.class).build();
  }

  @Bean(name = POOL)
  public ConnectionPool jmxPool(@Qualifier(DATASOURCE) DataSource dataSource) throws SQLException {
    return this.createJmxPool(dataSource);
  }

  @Bean(name = ENTITY_MANAGER)
  public LocalContainerEntityManagerFactoryBean entityManager(
      @Qualifier(PROPERTIES) JpaProperties properties,
      @Qualifier(DATASOURCE) DataSource datasource) {
    return this.buildEntityManager(properties, datasource);
  }


  @Bean(name = TRANSACTION_MANAGER)
  public PlatformTransactionManager transactionManager(
      @Qualifier(ENTITY_MANAGER) LocalContainerEntityManagerFactoryBean entityManager) {
    return this.buildTransactionManager(entityManager);
  }


  @Override
  protected String entityPackage() {
    return ENTITY_PACKAGE;
  }

  @Override
  protected String unitName() {
    return UNIT;
  }
}
