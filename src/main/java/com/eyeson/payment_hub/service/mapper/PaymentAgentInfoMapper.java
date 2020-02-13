package com.eyeson.payment_hub.service.mapper;


import com.eyeson.payment_hub.domain.*;
import com.eyeson.payment_hub.service.dto.PaymentAgentInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentAgentInfo} and its DTO {@link PaymentAgentInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentAgentInfoMapper extends EntityMapper<PaymentAgentInfoDTO, PaymentAgentInfo> {



    default PaymentAgentInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentAgentInfo paymentAgentInfo = new PaymentAgentInfo();
        paymentAgentInfo.setId(id);
        return paymentAgentInfo;
    }
}
