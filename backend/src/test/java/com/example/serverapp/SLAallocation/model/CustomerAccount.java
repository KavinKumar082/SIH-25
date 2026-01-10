package com.example.serverapp.allocation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_accounts")
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "outstanding_amount")
    private Double outstandingAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "ageing_bucket")
    private AgeingBucket ageingBucket;

    @Column(name = "delinquency_score")
    private Double delinquencyScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "collection_stage")
    private CollectionStage collectionStage;

    public enum AgeingBucket {
        ZERO_TO_30,
        THIRTY_ONE_TO_60,
        SIXTY_ONE_TO_90,
        NINETY_PLUS
    }

    public enum CollectionStage {
        PRE_DCA, ACTIVE_DCA, LEGAL
    }

    // ✅ GETTERS (REQUIRED)
    public Long getAccountId() {
        return accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public AgeingBucket getAgeingBucket() {
        return ageingBucket;
    }

    public Double getDelinquencyScore() {
        return delinquencyScore;
    }

    public CollectionStage getCollectionStage() {
        return collectionStage;
    }

    // ✅ SETTERS (REQUIRED)
    public void setCollectionStage(CollectionStage collectionStage) {
        this.collectionStage = collectionStage;
    }
}
