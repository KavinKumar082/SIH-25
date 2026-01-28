package com.example.backend.domain.entity.customer;

import com.example.backend.converter.customer.AgeingBucketConverter;
import com.example.backend.converter.customer.CustomerCollectionStageConverter;
import com.example.backend.converter.customer.RiskSegmentConverter;
import com.example.backend.domain.enums.customer.AgeingBucket;
import com.example.backend.domain.enums.customer.CollectionStage;
import com.example.backend.domain.enums.customer.RiskSegment;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Customer_Account")
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false, updatable = false)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "credit_limit", precision = 15, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "outstanding_amount", precision = 15, scale = 2)
    private BigDecimal outstandingAmount;

    @Convert(converter = AgeingBucketConverter.class)
    @Column(
        name = "ageing_bucket",
        columnDefinition = "ENUM('0-30','31-60','61-90','90+')"
    )
    private AgeingBucket ageingBucket;


    @Convert(converter = RiskSegmentConverter.class)
    @Column(
        name = "risk_segment",
        columnDefinition = "ENUM('Low','Medium','High')"
    )
    private RiskSegment riskSegment;


    @Column(name = "delinquency_score")
    private Integer delinquencyScore;

    @Convert(converter = CustomerCollectionStageConverter.class)
    @Column(
        name = "collection_stage",
        columnDefinition = "ENUM('Pre_DCA','Active','Legal')"
    )
    private CollectionStage collectionStage;


    // ---------- Getters & Setters ----------

    public Long getAccountId() {
        return accountId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public AgeingBucket getAgeingBucket() {
        return ageingBucket;
    }

    public void setAgeingBucket(AgeingBucket ageingBucket) {
        this.ageingBucket = ageingBucket;
    }

    public RiskSegment getRiskSegment() {
        return riskSegment;
    }

    public void setRiskSegment(RiskSegment riskSegment) {
        this.riskSegment = riskSegment;
    }

    public Integer getDelinquencyScore() {
        return delinquencyScore;
    }

    public void setDelinquencyScore(Integer delinquencyScore) {
        this.delinquencyScore = delinquencyScore;
    }

    public CollectionStage getCollectionStage() {
        return collectionStage;
    }

    public void setCollectionStage(CollectionStage collectionStage) {
        this.collectionStage = collectionStage;
    }
}
