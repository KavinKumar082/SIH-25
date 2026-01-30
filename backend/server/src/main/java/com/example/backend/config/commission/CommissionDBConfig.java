package com.example.backend.config.commission;

import java.util.Objects;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.backend.repository.commission",
        entityManagerFactoryRef = "commissionEntityManagerFactory",
        transactionManagerRef = "commissionTransactionManager"
)
public class CommissionDBConfig {

    @Bean(name = "commissionDataSource")
    @ConfigurationProperties(prefix = "commission.datasource")
    public DataSource commissionDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "commissionEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean commissionEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("commissionDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.example.backend.domain.entity.commission")
                .persistenceUnit("commissionPU")
                .build();
    }

    @Bean(name = "commissionTransactionManager")
    public PlatformTransactionManager commissionTransactionManager(
            @Qualifier("commissionEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean emfBean) {

        return new JpaTransactionManager(
                Objects.requireNonNull(
                        emfBean.getObject(),
                        "Commission EntityManagerFactory must not be null"
                )
        );
    }
}
