package com.crl.order.domain.converter;

import com.crl.order.domain.Order;
import com.crl.order.domain.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderConverter {

    OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    Order dtoToDo(OrderDTO orderDTO);
}
