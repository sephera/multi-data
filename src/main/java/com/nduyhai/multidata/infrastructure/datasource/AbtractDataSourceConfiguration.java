package com.nduyhai.multidata.infrastructure.datasource;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public abstract class AbtractDataSourceConfiguration {

    protected EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(final JpaProperties firstProperties,
                                                                            final PersistenceUnitManager persistenceUnitManager) {
        JpaVendorAdapter jpaVendorAdapter = this.createJpaVendorAdapter(firstProperties);

        return new EntityManagerFactoryBuilder(jpaVendorAdapter,
                firstProperties.getProperties(), persistenceUnitManager);
    }

    private JpaVendorAdapter createJpaVendorAdapter(final JpaProperties jpaProperties) {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.getDatabase());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }
}
