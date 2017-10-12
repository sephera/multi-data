package com.nduyhai.multidata.infrastructure.datasource;

import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

public abstract class AbstractMultipleDataSource {

    @Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;

    protected ConnectionPool jmxPool(DataSource dataSource) throws SQLException {
        return ((DataSourceProxy) dataSource).createPool().getJmxPool();
    }

    protected EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(final JpaProperties properties,
                                                                            final PersistenceUnitManager persistenceUnitManager) {
        JpaVendorAdapter jpaVendorAdapter = this.createJpaVendorAdapter(properties);

        return new EntityManagerFactoryBuilder(jpaVendorAdapter,
                properties.getProperties(), persistenceUnitManager);
    }

    private JpaVendorAdapter createJpaVendorAdapter(final JpaProperties jpaProperties) {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.getDatabase());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }

    protected LocalContainerEntityManagerFactoryBean buildEntityManager(final JpaProperties properties, final DataSource datasource) {
        EntityManagerFactoryBuilder builder = this.createEntityManagerFactoryBuilder(properties, this.persistenceUnitManager);
        return builder
                .dataSource(datasource)
                .packages(this.entityPackage())
                .persistenceUnit(this.unitName())
                .build();
    }

    protected PlatformTransactionManager buildTransactionManager(final LocalContainerEntityManagerFactoryBean  entityManager) {
        return new JpaTransactionManager(entityManager.getObject());
    }

    protected abstract String entityPackage();

    protected abstract String unitName();
}
