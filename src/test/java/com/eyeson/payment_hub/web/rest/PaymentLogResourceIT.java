package com.eyeson.payment_hub.web.rest;

import com.eyeson.payment_hub.GlobalPaymentHubApp;
import com.eyeson.payment_hub.domain.PaymentLog;
import com.eyeson.payment_hub.repository.PaymentLogRepository;
import com.eyeson.payment_hub.repository.search.PaymentLogSearchRepository;
import com.eyeson.payment_hub.service.PaymentLogService;
import com.eyeson.payment_hub.service.dto.PaymentLogDTO;
import com.eyeson.payment_hub.service.mapper.PaymentLogMapper;
import com.eyeson.payment_hub.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.eyeson.payment_hub.web.rest.TestUtil.sameInstant;
import static com.eyeson.payment_hub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eyeson.payment_hub.domain.enumeration.PaymentStatus;
/**
 * Integration tests for the {@link PaymentLogResource} REST controller.
 */
@SpringBootTest(classes = GlobalPaymentHubApp.class)
public class PaymentLogResourceIT {

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_CONSUMER_APP_ID = 1L;
    private static final Long UPDATED_CONSUMER_APP_ID = 2L;

    private static final String DEFAULT_PAYMENT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_SUBJECT = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final String DEFAULT_TRACE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TRACE_CODE = "BBBBBBBBBB";

    private static final PaymentStatus DEFAULT_STATUS = PaymentStatus.ACTIVE;
    private static final PaymentStatus UPDATED_STATUS = PaymentStatus.FAILED;

    private static final String DEFAULT_DECRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final String DEFAULT_RESPONSE_INFO = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_INFO = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_INFO = "BBBBBBBBBB";

    @Autowired
    private PaymentLogRepository paymentLogRepository;

    @Autowired
    private PaymentLogMapper paymentLogMapper;

    @Autowired
    private PaymentLogService paymentLogService;

    /**
     * This repository is mocked in the com.eyeson.payment_hub.repository.search test package.
     *
     * @see com.eyeson.payment_hub.repository.search.PaymentLogSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentLogSearchRepository mockPaymentLogSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPaymentLogMockMvc;

    private PaymentLog paymentLog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentLogResource paymentLogResource = new PaymentLogResource(paymentLogService);
        this.restPaymentLogMockMvc = MockMvcBuilders.standaloneSetup(paymentLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentLog createEntity(EntityManager em) {
        PaymentLog paymentLog = new PaymentLog()
            .createDate(DEFAULT_CREATE_DATE)
            .consumerAppId(DEFAULT_CONSUMER_APP_ID)
            .paymentSubject(DEFAULT_PAYMENT_SUBJECT)
            .customerId(DEFAULT_CUSTOMER_ID)
            .traceCode(DEFAULT_TRACE_CODE)
            .status(DEFAULT_STATUS)
            .decription(DEFAULT_DECRIPTION)
            .amount(DEFAULT_AMOUNT)
            .responseInfo(DEFAULT_RESPONSE_INFO)
            .requestInfo(DEFAULT_REQUEST_INFO);
        return paymentLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentLog createUpdatedEntity(EntityManager em) {
        PaymentLog paymentLog = new PaymentLog()
            .createDate(UPDATED_CREATE_DATE)
            .consumerAppId(UPDATED_CONSUMER_APP_ID)
            .paymentSubject(UPDATED_PAYMENT_SUBJECT)
            .customerId(UPDATED_CUSTOMER_ID)
            .traceCode(UPDATED_TRACE_CODE)
            .status(UPDATED_STATUS)
            .decription(UPDATED_DECRIPTION)
            .amount(UPDATED_AMOUNT)
            .responseInfo(UPDATED_RESPONSE_INFO)
            .requestInfo(UPDATED_REQUEST_INFO);
        return paymentLog;
    }

    @BeforeEach
    public void initTest() {
        paymentLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentLog() throws Exception {
        int databaseSizeBeforeCreate = paymentLogRepository.findAll().size();

        // Create the PaymentLog
        PaymentLogDTO paymentLogDTO = paymentLogMapper.toDto(paymentLog);
        restPaymentLogMockMvc.perform(post("/api/payment-logs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLogDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentLog in the database
        List<PaymentLog> paymentLogList = paymentLogRepository.findAll();
        assertThat(paymentLogList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentLog testPaymentLog = paymentLogList.get(paymentLogList.size() - 1);
        assertThat(testPaymentLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPaymentLog.getConsumerAppId()).isEqualTo(DEFAULT_CONSUMER_APP_ID);
        assertThat(testPaymentLog.getPaymentSubject()).isEqualTo(DEFAULT_PAYMENT_SUBJECT);
        assertThat(testPaymentLog.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testPaymentLog.getTraceCode()).isEqualTo(DEFAULT_TRACE_CODE);
        assertThat(testPaymentLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentLog.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testPaymentLog.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPaymentLog.getResponseInfo()).isEqualTo(DEFAULT_RESPONSE_INFO);
        assertThat(testPaymentLog.getRequestInfo()).isEqualTo(DEFAULT_REQUEST_INFO);

        // Validate the PaymentLog in Elasticsearch
        verify(mockPaymentLogSearchRepository, times(1)).save(testPaymentLog);
    }

    @Test
    @Transactional
    public void createPaymentLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentLogRepository.findAll().size();

        // Create the PaymentLog with an existing ID
        paymentLog.setId(1L);
        PaymentLogDTO paymentLogDTO = paymentLogMapper.toDto(paymentLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentLogMockMvc.perform(post("/api/payment-logs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentLog in the database
        List<PaymentLog> paymentLogList = paymentLogRepository.findAll();
        assertThat(paymentLogList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentLog in Elasticsearch
        verify(mockPaymentLogSearchRepository, times(0)).save(paymentLog);
    }


    @Test
    @Transactional
    public void getAllPaymentLogs() throws Exception {
        // Initialize the database
        paymentLogRepository.saveAndFlush(paymentLog);

        // Get all the paymentLogList
        restPaymentLogMockMvc.perform(get("/api/payment-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].consumerAppId").value(hasItem(DEFAULT_CONSUMER_APP_ID.intValue())))
            .andExpect(jsonPath("$.[*].paymentSubject").value(hasItem(DEFAULT_PAYMENT_SUBJECT)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].traceCode").value(hasItem(DEFAULT_TRACE_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].responseInfo").value(hasItem(DEFAULT_RESPONSE_INFO)))
            .andExpect(jsonPath("$.[*].requestInfo").value(hasItem(DEFAULT_REQUEST_INFO)));
    }
    
    @Test
    @Transactional
    public void getPaymentLog() throws Exception {
        // Initialize the database
        paymentLogRepository.saveAndFlush(paymentLog);

        // Get the paymentLog
        restPaymentLogMockMvc.perform(get("/api/payment-logs/{id}", paymentLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentLog.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.consumerAppId").value(DEFAULT_CONSUMER_APP_ID.intValue()))
            .andExpect(jsonPath("$.paymentSubject").value(DEFAULT_PAYMENT_SUBJECT))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.traceCode").value(DEFAULT_TRACE_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.responseInfo").value(DEFAULT_RESPONSE_INFO))
            .andExpect(jsonPath("$.requestInfo").value(DEFAULT_REQUEST_INFO));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentLog() throws Exception {
        // Get the paymentLog
        restPaymentLogMockMvc.perform(get("/api/payment-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentLog() throws Exception {
        // Initialize the database
        paymentLogRepository.saveAndFlush(paymentLog);

        int databaseSizeBeforeUpdate = paymentLogRepository.findAll().size();

        // Update the paymentLog
        PaymentLog updatedPaymentLog = paymentLogRepository.findById(paymentLog.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentLog are not directly saved in db
        em.detach(updatedPaymentLog);
        updatedPaymentLog
            .createDate(UPDATED_CREATE_DATE)
            .consumerAppId(UPDATED_CONSUMER_APP_ID)
            .paymentSubject(UPDATED_PAYMENT_SUBJECT)
            .customerId(UPDATED_CUSTOMER_ID)
            .traceCode(UPDATED_TRACE_CODE)
            .status(UPDATED_STATUS)
            .decription(UPDATED_DECRIPTION)
            .amount(UPDATED_AMOUNT)
            .responseInfo(UPDATED_RESPONSE_INFO)
            .requestInfo(UPDATED_REQUEST_INFO);
        PaymentLogDTO paymentLogDTO = paymentLogMapper.toDto(updatedPaymentLog);

        restPaymentLogMockMvc.perform(put("/api/payment-logs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLogDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentLog in the database
        List<PaymentLog> paymentLogList = paymentLogRepository.findAll();
        assertThat(paymentLogList).hasSize(databaseSizeBeforeUpdate);
        PaymentLog testPaymentLog = paymentLogList.get(paymentLogList.size() - 1);
        assertThat(testPaymentLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPaymentLog.getConsumerAppId()).isEqualTo(UPDATED_CONSUMER_APP_ID);
        assertThat(testPaymentLog.getPaymentSubject()).isEqualTo(UPDATED_PAYMENT_SUBJECT);
        assertThat(testPaymentLog.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testPaymentLog.getTraceCode()).isEqualTo(UPDATED_TRACE_CODE);
        assertThat(testPaymentLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentLog.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testPaymentLog.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPaymentLog.getResponseInfo()).isEqualTo(UPDATED_RESPONSE_INFO);
        assertThat(testPaymentLog.getRequestInfo()).isEqualTo(UPDATED_REQUEST_INFO);

        // Validate the PaymentLog in Elasticsearch
        verify(mockPaymentLogSearchRepository, times(1)).save(testPaymentLog);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentLog() throws Exception {
        int databaseSizeBeforeUpdate = paymentLogRepository.findAll().size();

        // Create the PaymentLog
        PaymentLogDTO paymentLogDTO = paymentLogMapper.toDto(paymentLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentLogMockMvc.perform(put("/api/payment-logs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentLog in the database
        List<PaymentLog> paymentLogList = paymentLogRepository.findAll();
        assertThat(paymentLogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentLog in Elasticsearch
        verify(mockPaymentLogSearchRepository, times(0)).save(paymentLog);
    }

    @Test
    @Transactional
    public void deletePaymentLog() throws Exception {
        // Initialize the database
        paymentLogRepository.saveAndFlush(paymentLog);

        int databaseSizeBeforeDelete = paymentLogRepository.findAll().size();

        // Delete the paymentLog
        restPaymentLogMockMvc.perform(delete("/api/payment-logs/{id}", paymentLog.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentLog> paymentLogList = paymentLogRepository.findAll();
        assertThat(paymentLogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentLog in Elasticsearch
        verify(mockPaymentLogSearchRepository, times(1)).deleteById(paymentLog.getId());
    }

    @Test
    @Transactional
    public void searchPaymentLog() throws Exception {
        // Initialize the database
        paymentLogRepository.saveAndFlush(paymentLog);
        when(mockPaymentLogSearchRepository.search(queryStringQuery("id:" + paymentLog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentLog), PageRequest.of(0, 1), 1));
        // Search the paymentLog
        restPaymentLogMockMvc.perform(get("/api/_search/payment-logs?query=id:" + paymentLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].consumerAppId").value(hasItem(DEFAULT_CONSUMER_APP_ID.intValue())))
            .andExpect(jsonPath("$.[*].paymentSubject").value(hasItem(DEFAULT_PAYMENT_SUBJECT)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].traceCode").value(hasItem(DEFAULT_TRACE_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].responseInfo").value(hasItem(DEFAULT_RESPONSE_INFO)))
            .andExpect(jsonPath("$.[*].requestInfo").value(hasItem(DEFAULT_REQUEST_INFO)));
    }
}
