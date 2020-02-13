package com.eyeson.payment_hub.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eyeson.payment_hub.web.rest.TestUtil;

public class PaymentAgentInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentAgentInfoDTO.class);
        PaymentAgentInfoDTO paymentAgentInfoDTO1 = new PaymentAgentInfoDTO();
        paymentAgentInfoDTO1.setId(1L);
        PaymentAgentInfoDTO paymentAgentInfoDTO2 = new PaymentAgentInfoDTO();
        assertThat(paymentAgentInfoDTO1).isNotEqualTo(paymentAgentInfoDTO2);
        paymentAgentInfoDTO2.setId(paymentAgentInfoDTO1.getId());
        assertThat(paymentAgentInfoDTO1).isEqualTo(paymentAgentInfoDTO2);
        paymentAgentInfoDTO2.setId(2L);
        assertThat(paymentAgentInfoDTO1).isNotEqualTo(paymentAgentInfoDTO2);
        paymentAgentInfoDTO1.setId(null);
        assertThat(paymentAgentInfoDTO1).isNotEqualTo(paymentAgentInfoDTO2);
    }
}
