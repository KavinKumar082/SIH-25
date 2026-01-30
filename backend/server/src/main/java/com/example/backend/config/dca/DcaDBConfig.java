package com.example.backend.config.dca;

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
        basePackages = "com.example.backend.repository.dca",
        entityManagerFactoryRef = "dcaEntityManagerFactory",
        transactionManagerRef = "dcaTransactionManager"
)
public class DcaDBConfig {

    @Bean(name = "dcaDataSource")
    @ConfigurationProperties(prefix = "dca.datasource")
    public DataSource dcaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dcaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dcaEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dcaDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.example.backend.domain.entity.dca")
                .persistenceUnit("dcaPU")
                .build();
    }

    @Bean(name = "dcaTransactionManager")
    public PlatformTransactionManager dcaTransactionManager(
            @Qualifier("dcaEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean emfBean) {

        return new JpaTransactionManager(
                Objects.requireNonNull(
                        emfBean.getObject(),
                        "DCA EntityManagerFactory must not be null"
                )
        );
    }
}
