package com.example.serverapp.allocation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.serverapp.allocation.model.CustomerAccount;

public interface CustomerAccountRepository
        extends JpaRepository<CustomerAccount, Long> {

    List<CustomerAccount> findByCollectionStage(
            CustomerAccount.CollectionStage stage
    );
}
