package com.eyeson.payment_hub.service;

import com.eyeson.payment_hub.service.dto.PaymentLogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.eyeson.payment_hub.domain.PaymentLog}.
 */
public interface PaymentLogService {

    /**
     * Save a paymentLog.
     *
     * @param paymentLogDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentLogDTO save(PaymentLogDTO paymentLogDTO);

    /**
     * Get all the paymentLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentLogDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paymentLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentLogDTO> findOne(Long id);

    /**
     * Delete the "id" paymentLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the paymentLog corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentLogDTO> search(String query, Pageable pageable);
}
