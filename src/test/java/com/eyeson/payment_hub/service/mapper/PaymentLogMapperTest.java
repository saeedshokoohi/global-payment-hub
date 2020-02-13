package com.eyeson.payment_hub.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentLogMapperTest {

    private PaymentLogMapper paymentLogMapper;

    @BeforeEach
    public void setUp() {
        paymentLogMapper = new PaymentLogMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paymentLogMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentLogMapper.fromId(null)).isNull();
    }
}
