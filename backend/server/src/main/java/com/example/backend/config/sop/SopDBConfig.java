package com.example.backend.config.sop;

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
        basePackages = "com.example.backend.repository.sop",
        entityManagerFactoryRef = "sopEntityManagerFactory",
        transactionManagerRef = "sopTransactionManager"
)
public class SopDBConfig {

    @Bean(name = "sopDataSource")
    @ConfigurationProperties(prefix = "sop.datasource")
    public DataSource sopDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sopEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sopEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sopDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.example.backend.domain.entity.sop")
                .persistenceUnit("sopPU")
                .build();
    }

    @Bean(name = "sopTransactionManager")
    public PlatformTransactionManager sopTransactionManager(
            @Qualifier("sopEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean emfBean) {

        return new JpaTransactionManager(
                Objects.requireNonNull(
                        emfBean.getObject(),
                        "SOP EntityManagerFactory must not be null"
                )
        );
    }
}
