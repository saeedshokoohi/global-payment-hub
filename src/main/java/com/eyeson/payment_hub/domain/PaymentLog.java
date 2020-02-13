package com.eyeson.payment_hub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

import com.eyeson.payment_hub.domain.enumeration.PaymentStatus;

/**
 * A PaymentLog.
 */
@Entity
@Table(name = "payment_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paymentlog")
public class PaymentLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "consumer_app_id")
    private Long consumerAppId;

    @Column(name = "payment_subject")
    private String paymentSubject;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "trace_code")
    private String traceCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "decription")
    private String decription;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "response_info")
    private String responseInfo;

    @Column(name = "request_info")
    private String requestInfo;

    @ManyToOne
    @JsonIgnoreProperties("paymentLogs")
    private PaymentAgentInfo paymentAgentInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public PaymentLog createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getConsumerAppId() {
        return consumerAppId;
    }

    public PaymentLog consumerAppId(Long consumerAppId) {
        this.consumerAppId = consumerAppId;
        return this;
    }

    public void setConsumerAppId(Long consumerAppId) {
        this.consumerAppId = consumerAppId;
    }

    public String getPaymentSubject() {
        return paymentSubject;
    }

    public PaymentLog paymentSubject(String paymentSubject) {
        this.paymentSubject = paymentSubject;
        return this;
    }

    public void setPaymentSubject(String paymentSubject) {
        this.paymentSubject = paymentSubject;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public PaymentLog customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTraceCode() {
        return traceCode;
    }

    public PaymentLog traceCode(String traceCode) {
        this.traceCode = traceCode;
        return this;
    }

    public void setTraceCode(String traceCode) {
        this.traceCode = traceCode;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public PaymentLog status(PaymentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getDecription() {
        return decription;
    }

    public PaymentLog decription(String decription) {
        this.decription = decription;
        return this;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Long getAmount() {
        return amount;
    }

    public PaymentLog amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public PaymentLog responseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
        return this;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public PaymentLog requestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
        return this;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public PaymentAgentInfo getPaymentAgentInfo() {
        return paymentAgentInfo;
    }

    public PaymentLog paymentAgentInfo(PaymentAgentInfo paymentAgentInfo) {
        this.paymentAgentInfo = paymentAgentInfo;
        return this;
    }

    public void setPaymentAgentInfo(PaymentAgentInfo paymentAgentInfo) {
        this.paymentAgentInfo = paymentAgentInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentLog)) {
            return false;
        }
        return id != null && id.equals(((PaymentLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaymentLog{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", consumerAppId=" + getConsumerAppId() +
            ", paymentSubject='" + getPaymentSubject() + "'" +
            ", customerId=" + getCustomerId() +
            ", traceCode='" + getTraceCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", decription='" + getDecription() + "'" +
            ", amount=" + getAmount() +
            ", responseInfo='" + getResponseInfo() + "'" +
            ", requestInfo='" + getRequestInfo() + "'" +
            "}";
    }
}
