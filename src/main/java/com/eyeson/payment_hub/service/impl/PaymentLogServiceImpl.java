package com.eyeson.payment_hub.service.impl;

import com.eyeson.payment_hub.service.PaymentLogService;
import com.eyeson.payment_hub.domain.PaymentLog;
import com.eyeson.payment_hub.repository.PaymentLogRepository;
import com.eyeson.payment_hub.repository.search.PaymentLogSearchRepository;
import com.eyeson.payment_hub.service.dto.PaymentLogDTO;
import com.eyeson.payment_hub.service.mapper.PaymentLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaymentLog}.
 */
@Service
@Transactional
public class PaymentLogServiceImpl implements PaymentLogService {

    private final Logger log = LoggerFactory.getLogger(PaymentLogServiceImpl.class);

    private final PaymentLogRepository paymentLogRepository;

    private final PaymentLogMapper paymentLogMapper;

    private final PaymentLogSearchRepository paymentLogSearchRepository;

    public PaymentLogServiceImpl(PaymentLogRepository paymentLogRepository, PaymentLogMapper paymentLogMapper, PaymentLogSearchRepository paymentLogSearchRepository) {
        this.paymentLogRepository = paymentLogRepository;
        this.paymentLogMapper = paymentLogMapper;
        this.paymentLogSearchRepository = paymentLogSearchRepository;
    }

    /**
     * Save a paymentLog.
     *
     * @param paymentLogDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaymentLogDTO save(PaymentLogDTO paymentLogDTO) {
        log.debug("Request to save PaymentLog : {}", paymentLogDTO);
        PaymentLog paymentLog = paymentLogMapper.toEntity(paymentLogDTO);
        paymentLog = paymentLogRepository.save(paymentLog);
        PaymentLogDTO result = paymentLogMapper.toDto(paymentLog);
        paymentLogSearchRepository.save(paymentLog);
        return result;
    }

    /**
     * Get all the paymentLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentLogs");
        return paymentLogRepository.findAll(pageable)
            .map(paymentLogMapper::toDto);
    }

    /**
     * Get one paymentLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentLogDTO> findOne(Long id) {
        log.debug("Request to get PaymentLog : {}", id);
        return paymentLogRepository.findById(id)
            .map(paymentLogMapper::toDto);
    }

    /**
     * Delete the paymentLog by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentLog : {}", id);
        paymentLogRepository.deleteById(id);
        paymentLogSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentLog corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentLogDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentLogs for query {}", query);
        return paymentLogSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentLogMapper::toDto);
    }
}
