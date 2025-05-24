package com.dio.labpadroesjava.delivery.strategy;

import com.dio.labpadroesjava.delivery.config.DeliverySystemConfig;
import com.dio.labpadroesjava.delivery.model.Order;

// Padrão: Strategy (Estratégia Concreta)
public class StandardDelivery implements DeliveryStrategy {

    @Override
    public void deliver(Order order) {
        System.out.println("Realizando entrega padrão para o pedido: " + order.getOrderId());
        // Lógica de entrega padrão (ex: agendar com transportadora comum)
    }

    @Override
    public double calculateDeliveryCost(Order order) {
        // Exemplo: custo baseado na distância do pedido e na configuração do sistema
        double distance = order.getDeliveryDistanceKm();
        double costPerKm = DeliverySystemConfig.getInstance().getStandardDeliveryCostPerKm();
        return distance * costPerKm;
    }
}