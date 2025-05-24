package com.dio.labpadroesjava.delivery.strategy;

import com.dio.labpadroesjava.delivery.model.Order;

// Padrão: Strategy (Estratégia Concreta)
public class ExpressDelivery implements DeliveryStrategy {

    private static final double EXPRESS_SURCHARGE_PERCENTAGE = 0.20; // 20% a mais

    @Override
    public void deliver(Order order) {
        System.out.println("Realizando entrega expressa (prioritária) para o pedido: " + order.getOrderId());
        // Lógica de entrega expressa (ex: agendar com motoboy)
    }

    @Override
    public double calculateDeliveryCost(Order order) {
        // Custo da entrega padrão mais uma sobretaxa expressa
        StandardDelivery standard = new StandardDelivery(); // Reutiliza a lógica da entrega padrão
        double baseCost = standard.calculateDeliveryCost(order);
        return baseCost * (1 + EXPRESS_SURCHARGE_PERCENTAGE);
    }
}