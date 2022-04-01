package com.example.maintenance.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * Конфигурация утоняет связь EM смапленного на пакеты entity и repository с конкретной БД
 * В проекте может быть несколько БД
 */
@EnableJpaRepositories(
        entityManagerFactoryRef = DatabasePgsqlConfig.ENTITY_MANAGER_FACTORY,
        basePackages = DatabasePgsqlConfig.JPA_REPOSITORY_PACKAGE
)
@Configuration
public class DatabasePgsqlConfig {
    public static final String JPA_REPOSITORY_PACKAGE = "com.example.maintenance.repository";
    public static final String ENTITY_PACKAGE = "com.example.maintenance.entity";
    public static final String ENTITY_MANAGER_FACTORY = "pgsqlEntityManagerFactory";
    public static final String DATA_SOURCE = "pgsqlDataSource";

    @Bean(DATA_SOURCE)
    public DataSource appDataSource(PgsqlParameters pgsqlParameters) {
        return DataSourceBuilder
                .create()
                .username(pgsqlParameters.getUser())
                .password(pgsqlParameters.getPassword())
                .url(pgsqlParameters.getUrl())
                .driverClassName(pgsqlParameters.getDriver())
                .build();
    }

    @Bean(ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean appEntityManager(
            @Qualifier(DATA_SOURCE) DataSource dataSource
    ) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName(ENTITY_MANAGER_FACTORY);
        em.setPackagesToScan(ENTITY_PACKAGE);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }
}
