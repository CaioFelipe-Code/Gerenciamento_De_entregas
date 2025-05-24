package com.dio.labpadroesjava.delivery.strategy;

import com.dio.labpadroesjava.delivery.model.Order;

// Padrão: Strategy (Estratégia Concreta)
public class PickupDelivery implements DeliveryStrategy {

    @Override
    public void deliver(Order order) {
        System.out.println("Pedido " + order.getOrderId() + " pronto para retirada no local.");
        // Lógica para notificar cliente que o pedido está pronto para retirada
    }

    @Override
    public double calculateDeliveryCost(Order order) {
        return 0.0; // Sem custo de entrega para retirada
    }
}