package com.eyeson.payment_hub.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PaymentLogSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PaymentLogSearchRepositoryMockConfiguration {

    @MockBean
    private PaymentLogSearchRepository mockPaymentLogSearchRepository;

}
