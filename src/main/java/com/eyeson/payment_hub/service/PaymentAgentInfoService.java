package com.eyeson.payment_hub.service;

import com.eyeson.payment_hub.service.dto.PaymentAgentInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.eyeson.payment_hub.domain.PaymentAgentInfo}.
 */
public interface PaymentAgentInfoService {

    /**
     * Save a paymentAgentInfo.
     *
     * @param paymentAgentInfoDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentAgentInfoDTO save(PaymentAgentInfoDTO paymentAgentInfoDTO);

    /**
     * Get all the paymentAgentInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentAgentInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paymentAgentInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentAgentInfoDTO> findOne(Long id);

    /**
     * Delete the "id" paymentAgentInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the paymentAgentInfo corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentAgentInfoDTO> search(String query, Pageable pageable);
}
