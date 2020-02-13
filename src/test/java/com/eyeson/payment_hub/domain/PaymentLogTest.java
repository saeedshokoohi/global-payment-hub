package com.eyeson.payment_hub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eyeson.payment_hub.web.rest.TestUtil;

public class PaymentLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentLog.class);
        PaymentLog paymentLog1 = new PaymentLog();
        paymentLog1.setId(1L);
        PaymentLog paymentLog2 = new PaymentLog();
        paymentLog2.setId(paymentLog1.getId());
        assertThat(paymentLog1).isEqualTo(paymentLog2);
        paymentLog2.setId(2L);
        assertThat(paymentLog1).isNotEqualTo(paymentLog2);
        paymentLog1.setId(null);
        assertThat(paymentLog1).isNotEqualTo(paymentLog2);
    }
}
