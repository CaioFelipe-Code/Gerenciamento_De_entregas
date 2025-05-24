package com.dio.labpadroesjava.delivery.observer;

import com.dio.labpadroesjava.delivery.model.Order; // Importe a classe Order

// Padrão: Observer (Observador Concreto)
// Atualiza o sistema de logística interna
public class LogisticsUpdater implements OrderObserver { // Implementa a interface OrderObserver
    @Override
    public void update(Order order) {
        System.out.println("[Atualizador Logístico] Sistema interno atualizado: Pedido " + order.getOrderId()
                + " agora em status: " + order.getStatus());
    }
}