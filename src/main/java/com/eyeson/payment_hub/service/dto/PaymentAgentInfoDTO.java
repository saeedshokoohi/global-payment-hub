package com.eyeson.payment_hub.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.eyeson.payment_hub.domain.enumeration.PaymentAgentType;
import com.eyeson.payment_hub.domain.enumeration.PaymentAgentStatus;

/**
 * A DTO for the {@link com.eyeson.payment_hub.domain.PaymentAgentInfo} entity.
 */
public class PaymentAgentInfoDTO implements Serializable {

    private Long id;

    private String title;

    private ZonedDateTime createDate;

    private PaymentAgentType agentType;

    private String username;

    private String password;

    private Long customerId;

    private String token;

    private String paymentUrl;

    private String defaultReturnUrl;

    private PaymentAgentStatus status;

    private Boolean isDeleted;

    private String description;

    private String agentConfig;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public PaymentAgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(PaymentAgentType agentType) {
        this.agentType = agentType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getDefaultReturnUrl() {
        return defaultReturnUrl;
    }

    public void setDefaultReturnUrl(String defaultReturnUrl) {
        this.defaultReturnUrl = defaultReturnUrl;
    }

    public PaymentAgentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentAgentStatus status) {
        this.status = status;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgentConfig() {
        return agentConfig;
    }

    public void setAgentConfig(String agentConfig) {
        this.agentConfig = agentConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentAgentInfoDTO paymentAgentInfoDTO = (PaymentAgentInfoDTO) o;
        if (paymentAgentInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentAgentInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentAgentInfoDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", agentType='" + getAgentType() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", customerId=" + getCustomerId() +
            ", token='" + getToken() + "'" +
            ", paymentUrl='" + getPaymentUrl() + "'" +
            ", defaultReturnUrl='" + getDefaultReturnUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            ", description='" + getDescription() + "'" +
            ", agentConfig='" + getAgentConfig() + "'" +
            "}";
    }
}
