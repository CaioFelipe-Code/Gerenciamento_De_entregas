package com.dio.labpadroesjava.delivery.facade;

import com.dio.labpadroesjava.delivery.model.Order;
import com.dio.labpadroesjava.delivery.observer.CustomerNotifier;
import com.dio.labpadroesjava.delivery.observer.LogisticsUpdater;
import com.dio.labpadroesjava.delivery.strategy.DeliveryStrategy;
import com.dio.labpadroesjava.delivery.strategy.ExpressDelivery;
import com.dio.labpadroesjava.delivery.strategy.PickupDelivery;
import com.dio.labpadroesjava.delivery.strategy.StandardDelivery;

// Padrão: Facade
// Simplifica a interface para processamento de pedidos.
public class OrderProcessingFacade {

    public OrderProcessingFacade() {
        // Observadores podem ser injetados ou criados aqui, dependendo da necessidade
    }

    public void processOrder(Order order, String deliveryType) {
        System.out.println("\n--- Processando Pedido " + order.getOrderId() + " ---");

        // Adicionando observadores ao pedido. Idealmente, isso poderia ser feito uma
        // vez na criação do pedido ou via injeção de dependência.
        order.addObserver(new CustomerNotifier());
        order.addObserver(new LogisticsUpdater());

        // Altera o status inicial, o que notifica os observadores
        order.setStatus("PROCESSING");

        DeliveryStrategy strategy;

        // Atribui a estratégia de entrega com base no tipo solicitado
        switch (deliveryType.toLowerCase()) {
            case "standard":
                strategy = new StandardDelivery();
                break;
            case "express":
                strategy = new ExpressDelivery();
                break;
            case "pickup":
                strategy = new PickupDelivery();
                break;
            default:
                System.out.println("Tipo de entrega inválido. Usando entrega padrão.");
                strategy = new StandardDelivery();
                break;
        }

        double deliveryCost = strategy.calculateDeliveryCost(order);
        order.setDeliveryCost(deliveryCost);
        order.setDeliveryStrategy(strategy); // Associa a estratégia ao pedido

        System.out.printf("Custo da entrega para o pedido %s: R$ %.2f\n", order.getOrderId(), deliveryCost);

        // Simula o processo de entrega e altera o status, disparando as notificações
        order.setStatus("OUT_FOR_DELIVERY"); // Mudança de status via fachada
        strategy.deliver(order);
        order.setStatus("DELIVERED"); // Status final

        System.out.println("Pedido " + order.getOrderId() + " processado com sucesso!");
        System.out.println("Detalhes finais do pedido: " + order);
        System.out.println("------------------------------------");
    }
}