package com.example.backend.config.payment;

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
        basePackages = "com.example.backend.repository.payment",
        entityManagerFactoryRef = "paymentEntityManagerFactory",
        transactionManagerRef = "paymentTransactionManager"
)
public class PaymentDBConfig {

    @Bean(name = "paymentDataSource")
    @ConfigurationProperties(prefix = "payment.datasource")
    public DataSource paymentDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "paymentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean paymentEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("paymentDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.example.backend.domain.entity.payment")
                .persistenceUnit("paymentPU")
                .build();
    }

    @Bean(name = "paymentTransactionManager")
    public PlatformTransactionManager paymentTransactionManager(
            @Qualifier("paymentEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean emfBean) {

        return new JpaTransactionManager(
                Objects.requireNonNull(
                        emfBean.getObject(),
                        "Payment EntityManagerFactory must not be null"
                )
        );
    }
}
