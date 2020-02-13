package com.eyeson.payment_hub.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

import com.eyeson.payment_hub.domain.enumeration.PaymentAgentType;

import com.eyeson.payment_hub.domain.enumeration.PaymentAgentStatus;

/**
 * A PaymentAgentInfo.
 */
@Entity
@Table(name = "payment_agent_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paymentagentinfo")
public class PaymentAgentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "agent_type")
    private PaymentAgentType agentType;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "token")
    private String token;

    @Column(name = "payment_url")
    private String paymentUrl;

    @Column(name = "default_return_url")
    private String defaultReturnUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentAgentStatus status;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "description")
    private String description;

    @Column(name = "agent_config")
    private String agentConfig;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public PaymentAgentInfo title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public PaymentAgentInfo createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public PaymentAgentType getAgentType() {
        return agentType;
    }

    public PaymentAgentInfo agentType(PaymentAgentType agentType) {
        this.agentType = agentType;
        return this;
    }

    public void setAgentType(PaymentAgentType agentType) {
        this.agentType = agentType;
    }

    public String getUsername() {
        return username;
    }

    public PaymentAgentInfo username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public PaymentAgentInfo password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public PaymentAgentInfo customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public PaymentAgentInfo token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public PaymentAgentInfo paymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
        return this;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getDefaultReturnUrl() {
        return defaultReturnUrl;
    }

    public PaymentAgentInfo defaultReturnUrl(String defaultReturnUrl) {
        this.defaultReturnUrl = defaultReturnUrl;
        return this;
    }

    public void setDefaultReturnUrl(String defaultReturnUrl) {
        this.defaultReturnUrl = defaultReturnUrl;
    }

    public PaymentAgentStatus getStatus() {
        return status;
    }

    public PaymentAgentInfo status(PaymentAgentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PaymentAgentStatus status) {
        this.status = status;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public PaymentAgentInfo isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDescription() {
        return description;
    }

    public PaymentAgentInfo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgentConfig() {
        return agentConfig;
    }

    public PaymentAgentInfo agentConfig(String agentConfig) {
        this.agentConfig = agentConfig;
        return this;
    }

    public void setAgentConfig(String agentConfig) {
        this.agentConfig = agentConfig;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAgentInfo)) {
            return false;
        }
        return id != null && id.equals(((PaymentAgentInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaymentAgentInfo{" +
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
