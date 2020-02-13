package com.eyeson.payment_hub.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eyeson.payment_hub.web.rest.TestUtil;

public class PaymentAgentInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentAgentInfo.class);
        PaymentAgentInfo paymentAgentInfo1 = new PaymentAgentInfo();
        paymentAgentInfo1.setId(1L);
        PaymentAgentInfo paymentAgentInfo2 = new PaymentAgentInfo();
        paymentAgentInfo2.setId(paymentAgentInfo1.getId());
        assertThat(paymentAgentInfo1).isEqualTo(paymentAgentInfo2);
        paymentAgentInfo2.setId(2L);
        assertThat(paymentAgentInfo1).isNotEqualTo(paymentAgentInfo2);
        paymentAgentInfo1.setId(null);
        assertThat(paymentAgentInfo1).isNotEqualTo(paymentAgentInfo2);
    }
}
