package com.eyeson.payment_hub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eyeson.payment_hub.web.rest.TestUtil;

public class PaymentLogDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentLogDTO.class);
        PaymentLogDTO paymentLogDTO1 = new PaymentLogDTO();
        paymentLogDTO1.setId(1L);
        PaymentLogDTO paymentLogDTO2 = new PaymentLogDTO();
        assertThat(paymentLogDTO1).isNotEqualTo(paymentLogDTO2);
        paymentLogDTO2.setId(paymentLogDTO1.getId());
        assertThat(paymentLogDTO1).isEqualTo(paymentLogDTO2);
        paymentLogDTO2.setId(2L);
        assertThat(paymentLogDTO1).isNotEqualTo(paymentLogDTO2);
        paymentLogDTO1.setId(null);
        assertThat(paymentLogDTO1).isNotEqualTo(paymentLogDTO2);
    }
}
