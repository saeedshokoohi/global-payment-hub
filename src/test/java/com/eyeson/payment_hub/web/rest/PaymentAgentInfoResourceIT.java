package com.eyeson.payment_hub.web.rest;

import com.eyeson.payment_hub.GlobalPaymentHubApp;
import com.eyeson.payment_hub.domain.PaymentAgentInfo;
import com.eyeson.payment_hub.repository.PaymentAgentInfoRepository;
import com.eyeson.payment_hub.repository.search.PaymentAgentInfoSearchRepository;
import com.eyeson.payment_hub.service.PaymentAgentInfoService;
import com.eyeson.payment_hub.service.dto.PaymentAgentInfoDTO;
import com.eyeson.payment_hub.service.mapper.PaymentAgentInfoMapper;
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

import com.eyeson.payment_hub.domain.enumeration.PaymentAgentType;
import com.eyeson.payment_hub.domain.enumeration.PaymentAgentStatus;
/**
 * Integration tests for the {@link PaymentAgentInfoResource} REST controller.
 */
@SpringBootTest(classes = GlobalPaymentHubApp.class)
public class PaymentAgentInfoResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final PaymentAgentType DEFAULT_AGENT_TYPE = PaymentAgentType.PAYPING;
    private static final PaymentAgentType UPDATED_AGENT_TYPE = PaymentAgentType.ZARRINPAL;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_URL = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_RETURN_URL = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_RETURN_URL = "BBBBBBBBBB";

    private static final PaymentAgentStatus DEFAULT_STATUS = PaymentAgentStatus.ACTIVE;
    private static final PaymentAgentStatus UPDATED_STATUS = PaymentAgentStatus.EXPIRED;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_AGENT_CONFIG = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_CONFIG = "BBBBBBBBBB";

    @Autowired
    private PaymentAgentInfoRepository paymentAgentInfoRepository;

    @Autowired
    private PaymentAgentInfoMapper paymentAgentInfoMapper;

    @Autowired
    private PaymentAgentInfoService paymentAgentInfoService;

    /**
     * This repository is mocked in the com.eyeson.payment_hub.repository.search test package.
     *
     * @see com.eyeson.payment_hub.repository.search.PaymentAgentInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentAgentInfoSearchRepository mockPaymentAgentInfoSearchRepository;

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

    private MockMvc restPaymentAgentInfoMockMvc;

    private PaymentAgentInfo paymentAgentInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentAgentInfoResource paymentAgentInfoResource = new PaymentAgentInfoResource(paymentAgentInfoService);
        this.restPaymentAgentInfoMockMvc = MockMvcBuilders.standaloneSetup(paymentAgentInfoResource)
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
    public static PaymentAgentInfo createEntity(EntityManager em) {
        PaymentAgentInfo paymentAgentInfo = new PaymentAgentInfo()
            .title(DEFAULT_TITLE)
            .createDate(DEFAULT_CREATE_DATE)
            .agentType(DEFAULT_AGENT_TYPE)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .customerId(DEFAULT_CUSTOMER_ID)
            .token(DEFAULT_TOKEN)
            .paymentUrl(DEFAULT_PAYMENT_URL)
            .defaultReturnUrl(DEFAULT_DEFAULT_RETURN_URL)
            .status(DEFAULT_STATUS)
            .isDeleted(DEFAULT_IS_DELETED)
            .description(DEFAULT_DESCRIPTION)
            .agentConfig(DEFAULT_AGENT_CONFIG);
        return paymentAgentInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAgentInfo createUpdatedEntity(EntityManager em) {
        PaymentAgentInfo paymentAgentInfo = new PaymentAgentInfo()
            .title(UPDATED_TITLE)
            .createDate(UPDATED_CREATE_DATE)
            .agentType(UPDATED_AGENT_TYPE)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .customerId(UPDATED_CUSTOMER_ID)
            .token(UPDATED_TOKEN)
            .paymentUrl(UPDATED_PAYMENT_URL)
            .defaultReturnUrl(UPDATED_DEFAULT_RETURN_URL)
            .status(UPDATED_STATUS)
            .isDeleted(UPDATED_IS_DELETED)
            .description(UPDATED_DESCRIPTION)
            .agentConfig(UPDATED_AGENT_CONFIG);
        return paymentAgentInfo;
    }

    @BeforeEach
    public void initTest() {
        paymentAgentInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentAgentInfo() throws Exception {
        int databaseSizeBeforeCreate = paymentAgentInfoRepository.findAll().size();

        // Create the PaymentAgentInfo
        PaymentAgentInfoDTO paymentAgentInfoDTO = paymentAgentInfoMapper.toDto(paymentAgentInfo);
        restPaymentAgentInfoMockMvc.perform(post("/api/payment-agent-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentAgentInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentAgentInfo in the database
        List<PaymentAgentInfo> paymentAgentInfoList = paymentAgentInfoRepository.findAll();
        assertThat(paymentAgentInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentAgentInfo testPaymentAgentInfo = paymentAgentInfoList.get(paymentAgentInfoList.size() - 1);
        assertThat(testPaymentAgentInfo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPaymentAgentInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPaymentAgentInfo.getAgentType()).isEqualTo(DEFAULT_AGENT_TYPE);
        assertThat(testPaymentAgentInfo.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testPaymentAgentInfo.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPaymentAgentInfo.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testPaymentAgentInfo.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testPaymentAgentInfo.getPaymentUrl()).isEqualTo(DEFAULT_PAYMENT_URL);
        assertThat(testPaymentAgentInfo.getDefaultReturnUrl()).isEqualTo(DEFAULT_DEFAULT_RETURN_URL);
        assertThat(testPaymentAgentInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentAgentInfo.isIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testPaymentAgentInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentAgentInfo.getAgentConfig()).isEqualTo(DEFAULT_AGENT_CONFIG);

        // Validate the PaymentAgentInfo in Elasticsearch
        verify(mockPaymentAgentInfoSearchRepository, times(1)).save(testPaymentAgentInfo);
    }

    @Test
    @Transactional
    public void createPaymentAgentInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentAgentInfoRepository.findAll().size();

        // Create the PaymentAgentInfo with an existing ID
        paymentAgentInfo.setId(1L);
        PaymentAgentInfoDTO paymentAgentInfoDTO = paymentAgentInfoMapper.toDto(paymentAgentInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentAgentInfoMockMvc.perform(post("/api/payment-agent-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentAgentInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentAgentInfo in the database
        List<PaymentAgentInfo> paymentAgentInfoList = paymentAgentInfoRepository.findAll();
        assertThat(paymentAgentInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentAgentInfo in Elasticsearch
        verify(mockPaymentAgentInfoSearchRepository, times(0)).save(paymentAgentInfo);
    }


    @Test
    @Transactional
    public void getAllPaymentAgentInfos() throws Exception {
        // Initialize the database
        paymentAgentInfoRepository.saveAndFlush(paymentAgentInfo);

        // Get all the paymentAgentInfoList
        restPaymentAgentInfoMockMvc.perform(get("/api/payment-agent-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentAgentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].agentType").value(hasItem(DEFAULT_AGENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].paymentUrl").value(hasItem(DEFAULT_PAYMENT_URL)))
            .andExpect(jsonPath("$.[*].defaultReturnUrl").value(hasItem(DEFAULT_DEFAULT_RETURN_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].agentConfig").value(hasItem(DEFAULT_AGENT_CONFIG)));
    }
    
    @Test
    @Transactional
    public void getPaymentAgentInfo() throws Exception {
        // Initialize the database
        paymentAgentInfoRepository.saveAndFlush(paymentAgentInfo);

        // Get the paymentAgentInfo
        restPaymentAgentInfoMockMvc.perform(get("/api/payment-agent-infos/{id}", paymentAgentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentAgentInfo.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.agentType").value(DEFAULT_AGENT_TYPE.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.paymentUrl").value(DEFAULT_PAYMENT_URL))
            .andExpect(jsonPath("$.defaultReturnUrl").value(DEFAULT_DEFAULT_RETURN_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.agentConfig").value(DEFAULT_AGENT_CONFIG));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentAgentInfo() throws Exception {
        // Get the paymentAgentInfo
        restPaymentAgentInfoMockMvc.perform(get("/api/payment-agent-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentAgentInfo() throws Exception {
        // Initialize the database
        paymentAgentInfoRepository.saveAndFlush(paymentAgentInfo);

        int databaseSizeBeforeUpdate = paymentAgentInfoRepository.findAll().size();

        // Update the paymentAgentInfo
        PaymentAgentInfo updatedPaymentAgentInfo = paymentAgentInfoRepository.findById(paymentAgentInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentAgentInfo are not directly saved in db
        em.detach(updatedPaymentAgentInfo);
        updatedPaymentAgentInfo
            .title(UPDATED_TITLE)
            .createDate(UPDATED_CREATE_DATE)
            .agentType(UPDATED_AGENT_TYPE)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .customerId(UPDATED_CUSTOMER_ID)
            .token(UPDATED_TOKEN)
            .paymentUrl(UPDATED_PAYMENT_URL)
            .defaultReturnUrl(UPDATED_DEFAULT_RETURN_URL)
            .status(UPDATED_STATUS)
            .isDeleted(UPDATED_IS_DELETED)
            .description(UPDATED_DESCRIPTION)
            .agentConfig(UPDATED_AGENT_CONFIG);
        PaymentAgentInfoDTO paymentAgentInfoDTO = paymentAgentInfoMapper.toDto(updatedPaymentAgentInfo);

        restPaymentAgentInfoMockMvc.perform(put("/api/payment-agent-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentAgentInfoDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentAgentInfo in the database
        List<PaymentAgentInfo> paymentAgentInfoList = paymentAgentInfoRepository.findAll();
        assertThat(paymentAgentInfoList).hasSize(databaseSizeBeforeUpdate);
        PaymentAgentInfo testPaymentAgentInfo = paymentAgentInfoList.get(paymentAgentInfoList.size() - 1);
        assertThat(testPaymentAgentInfo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPaymentAgentInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPaymentAgentInfo.getAgentType()).isEqualTo(UPDATED_AGENT_TYPE);
        assertThat(testPaymentAgentInfo.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPaymentAgentInfo.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPaymentAgentInfo.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testPaymentAgentInfo.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testPaymentAgentInfo.getPaymentUrl()).isEqualTo(UPDATED_PAYMENT_URL);
        assertThat(testPaymentAgentInfo.getDefaultReturnUrl()).isEqualTo(UPDATED_DEFAULT_RETURN_URL);
        assertThat(testPaymentAgentInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentAgentInfo.isIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testPaymentAgentInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentAgentInfo.getAgentConfig()).isEqualTo(UPDATED_AGENT_CONFIG);

        // Validate the PaymentAgentInfo in Elasticsearch
        verify(mockPaymentAgentInfoSearchRepository, times(1)).save(testPaymentAgentInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentAgentInfo() throws Exception {
        int databaseSizeBeforeUpdate = paymentAgentInfoRepository.findAll().size();

        // Create the PaymentAgentInfo
        PaymentAgentInfoDTO paymentAgentInfoDTO = paymentAgentInfoMapper.toDto(paymentAgentInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentAgentInfoMockMvc.perform(put("/api/payment-agent-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentAgentInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentAgentInfo in the database
        List<PaymentAgentInfo> paymentAgentInfoList = paymentAgentInfoRepository.findAll();
        assertThat(paymentAgentInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentAgentInfo in Elasticsearch
        verify(mockPaymentAgentInfoSearchRepository, times(0)).save(paymentAgentInfo);
    }

    @Test
    @Transactional
    public void deletePaymentAgentInfo() throws Exception {
        // Initialize the database
        paymentAgentInfoRepository.saveAndFlush(paymentAgentInfo);

        int databaseSizeBeforeDelete = paymentAgentInfoRepository.findAll().size();

        // Delete the paymentAgentInfo
        restPaymentAgentInfoMockMvc.perform(delete("/api/payment-agent-infos/{id}", paymentAgentInfo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentAgentInfo> paymentAgentInfoList = paymentAgentInfoRepository.findAll();
        assertThat(paymentAgentInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentAgentInfo in Elasticsearch
        verify(mockPaymentAgentInfoSearchRepository, times(1)).deleteById(paymentAgentInfo.getId());
    }

    @Test
    @Transactional
    public void searchPaymentAgentInfo() throws Exception {
        // Initialize the database
        paymentAgentInfoRepository.saveAndFlush(paymentAgentInfo);
        when(mockPaymentAgentInfoSearchRepository.search(queryStringQuery("id:" + paymentAgentInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentAgentInfo), PageRequest.of(0, 1), 1));
        // Search the paymentAgentInfo
        restPaymentAgentInfoMockMvc.perform(get("/api/_search/payment-agent-infos?query=id:" + paymentAgentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentAgentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].agentType").value(hasItem(DEFAULT_AGENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].paymentUrl").value(hasItem(DEFAULT_PAYMENT_URL)))
            .andExpect(jsonPath("$.[*].defaultReturnUrl").value(hasItem(DEFAULT_DEFAULT_RETURN_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].agentConfig").value(hasItem(DEFAULT_AGENT_CONFIG)));
    }
}
