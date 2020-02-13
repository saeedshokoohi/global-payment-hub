package com.eyeson.payment_hub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentAgentInfoMapperTest {

    private PaymentAgentInfoMapper paymentAgentInfoMapper;

    @BeforeEach
    public void setUp() {
        paymentAgentInfoMapper = new PaymentAgentInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paymentAgentInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentAgentInfoMapper.fromId(null)).isNull();
    }
}
