package com.eyeson.payment_hub.repository.search;

import com.eyeson.payment_hub.domain.PaymentLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PaymentLog} entity.
 */
public interface PaymentLogSearchRepository extends ElasticsearchRepository<PaymentLog, Long> {
}
