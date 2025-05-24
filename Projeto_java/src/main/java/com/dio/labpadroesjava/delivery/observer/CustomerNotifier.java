package com.dio.labpadroesjava.delivery.observer;

import com.dio.labpadroesjava.delivery.model.Order;

// Padrão: Observer (Observador Concreto)
// Notifica o cliente sobre o status do pedido
public class CustomerNotifier implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println("[Notificador do Cliente] Pedido " + order.getOrderId() + " (Status: " + order.getStatus()
                + "). Prezado(a) " + order.getCustomerName() + ", seu pedido está com uma nova atualização!");
    }
}