package com.eyeson.payment_hub.service.mapper;


import com.eyeson.payment_hub.domain.*;
import com.eyeson.payment_hub.service.dto.PaymentLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentLog} and its DTO {@link PaymentLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {PaymentAgentInfoMapper.class})
public interface PaymentLogMapper extends EntityMapper<PaymentLogDTO, PaymentLog> {

    @Mapping(source = "paymentAgentInfo.id", target = "paymentAgentInfoId")
    PaymentLogDTO toDto(PaymentLog paymentLog);

    @Mapping(source = "paymentAgentInfoId", target = "paymentAgentInfo")
    PaymentLog toEntity(PaymentLogDTO paymentLogDTO);

    default PaymentLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setId(id);
        return paymentLog;
    }
}
