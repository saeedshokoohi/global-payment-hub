package com.eyeson.payment_hub.repository;

import com.eyeson.payment_hub.domain.PaymentLog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {

}
