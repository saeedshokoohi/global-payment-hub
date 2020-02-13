package com.eyeson.payment_hub.service.impl;

import com.eyeson.payment_hub.service.PaymentAgentInfoService;
import com.eyeson.payment_hub.domain.PaymentAgentInfo;
import com.eyeson.payment_hub.repository.PaymentAgentInfoRepository;
import com.eyeson.payment_hub.repository.search.PaymentAgentInfoSearchRepository;
import com.eyeson.payment_hub.service.dto.PaymentAgentInfoDTO;
import com.eyeson.payment_hub.service.mapper.PaymentAgentInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PaymentAgentInfo}.
 */
@Service
@Transactional
public class PaymentAgentInfoServiceImpl implements PaymentAgentInfoService {

    private final Logger log = LoggerFactory.getLogger(PaymentAgentInfoServiceImpl.class);

    private final PaymentAgentInfoRepository paymentAgentInfoRepository;

    private final PaymentAgentInfoMapper paymentAgentInfoMapper;

    private final PaymentAgentInfoSearchRepository paymentAgentInfoSearchRepository;

    public PaymentAgentInfoServiceImpl(PaymentAgentInfoRepository paymentAgentInfoRepository, PaymentAgentInfoMapper paymentAgentInfoMapper, PaymentAgentInfoSearchRepository paymentAgentInfoSearchRepository) {
        this.paymentAgentInfoRepository = paymentAgentInfoRepository;
        this.paymentAgentInfoMapper = paymentAgentInfoMapper;
        this.paymentAgentInfoSearchRepository = paymentAgentInfoSearchRepository;
    }

    /**
     * Save a paymentAgentInfo.
     *
     * @param paymentAgentInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaymentAgentInfoDTO save(PaymentAgentInfoDTO paymentAgentInfoDTO) {
        log.debug("Request to save PaymentAgentInfo : {}", paymentAgentInfoDTO);
        PaymentAgentInfo paymentAgentInfo = paymentAgentInfoMapper.toEntity(paymentAgentInfoDTO);
        paymentAgentInfo = paymentAgentInfoRepository.save(paymentAgentInfo);
        PaymentAgentInfoDTO result = paymentAgentInfoMapper.toDto(paymentAgentInfo);
        paymentAgentInfoSearchRepository.save(paymentAgentInfo);
        return result;
    }

    /**
     * Get all the paymentAgentInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentAgentInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentAgentInfos");
        return paymentAgentInfoRepository.findAll(pageable)
            .map(paymentAgentInfoMapper::toDto);
    }

    /**
     * Get one paymentAgentInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentAgentInfoDTO> findOne(Long id) {
        log.debug("Request to get PaymentAgentInfo : {}", id);
        return paymentAgentInfoRepository.findById(id)
            .map(paymentAgentInfoMapper::toDto);
    }

    /**
     * Delete the paymentAgentInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentAgentInfo : {}", id);
        paymentAgentInfoRepository.deleteById(id);
        paymentAgentInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentAgentInfo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentAgentInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentAgentInfos for query {}", query);
        return paymentAgentInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentAgentInfoMapper::toDto);
    }
}
