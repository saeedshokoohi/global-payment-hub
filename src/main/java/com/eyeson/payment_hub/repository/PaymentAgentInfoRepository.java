package com.eyeson.payment_hub.repository;

import com.eyeson.payment_hub.domain.PaymentAgentInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentAgentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentAgentInfoRepository extends JpaRepository<PaymentAgentInfo, Long> {

}
