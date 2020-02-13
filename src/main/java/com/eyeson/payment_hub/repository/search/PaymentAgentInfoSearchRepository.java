package com.eyeson.payment_hub.repository.search;

import com.eyeson.payment_hub.domain.PaymentAgentInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PaymentAgentInfo} entity.
 */
public interface PaymentAgentInfoSearchRepository extends ElasticsearchRepository<PaymentAgentInfo, Long> {
}
