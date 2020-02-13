package com.eyeson.payment_hub.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.eyeson.payment_hub.domain.enumeration.PaymentStatus;

/**
 * A DTO for the {@link com.eyeson.payment_hub.domain.PaymentLog} entity.
 */
public class PaymentLogDTO implements Serializable {

    private Long id;

    private ZonedDateTime createDate;

    private Long consumerAppId;

    private String paymentSubject;

    private Long customerId;

    private String traceCode;

    private PaymentStatus status;

    private String decription;

    private Long amount;

    private String responseInfo;

    private String requestInfo;


    private Long paymentAgentInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getConsumerAppId() {
        return consumerAppId;
    }

    public void setConsumerAppId(Long consumerAppId) {
        this.consumerAppId = consumerAppId;
    }

    public String getPaymentSubject() {
        return paymentSubject;
    }

    public void setPaymentSubject(String paymentSubject) {
        this.paymentSubject = paymentSubject;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTraceCode() {
        return traceCode;
    }

    public void setTraceCode(String traceCode) {
        this.traceCode = traceCode;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public Long getPaymentAgentInfoId() {
        return paymentAgentInfoId;
    }

    public void setPaymentAgentInfoId(Long paymentAgentInfoId) {
        this.paymentAgentInfoId = paymentAgentInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentLogDTO paymentLogDTO = (PaymentLogDTO) o;
        if (paymentLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentLogDTO{" +
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
            ", paymentAgentInfoId=" + getPaymentAgentInfoId() +
            "}";
    }
}
