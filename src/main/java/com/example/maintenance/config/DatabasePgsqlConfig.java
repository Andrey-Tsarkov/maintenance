package com.example.maintenance.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@EnableJpaRepositories(
        entityManagerFactoryRef = DatabasePgsqlConfig.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = DatabasePgsqlConfig.TRANSACTION_MANAGER,
        basePackages = DatabasePgsqlConfig.JPA_REPOSITORY_PACKAGE
)
@Configuration
public class DatabasePgsqlConfig {

    public static final String JPA_REPOSITORY_PACKAGE = "com.example.demo.repository.pgsql";
    public static final String ENTITY_PACKAGE = "com.example.demo.entity.pgsql";

    public static final String ENTITY_MANAGER_FACTORY = "pgsqlEntityManagerFactory";
    public static final String DATA_SOURCE = "pgsqlDataSource";
    public static final String TRANSACTION_MANAGER = "pgsqlTransactionManager";

    private final PgsqlConfig pgsqlConfig;

    public DatabasePgsqlConfig(PgsqlConfig pgsqlConfig) {
        this.pgsqlConfig = pgsqlConfig;
    }

    @Bean(DATA_SOURCE)
    @Primary
    public DataSource appDataSource(PgsqlConfig postgresConfig) {
        return DataSourceBuilder
                .create()
                .username(postgresConfig.getUser())
                .password(postgresConfig.getPassword())
                .url(postgresConfig.getUrl())
                .driverClassName(postgresConfig.getDriver())
                .build();
    }

    @Bean(ENTITY_MANAGER_FACTORY)
    @Primary
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
