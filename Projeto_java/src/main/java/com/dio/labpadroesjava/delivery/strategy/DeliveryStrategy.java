package com.dio.labpadroesjava.delivery.strategy;

import com.dio.labpadroesjava.delivery.model.Order;

// Padrão: Strategy
// Interface para definir a estratégia de entrega
public interface DeliveryStrategy {
    void deliver(Order order);

    double calculateDeliveryCost(Order order);
}