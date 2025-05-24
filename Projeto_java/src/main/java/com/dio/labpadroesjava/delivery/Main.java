package com.dio.labpadroesjava.delivery; // LINHA CORRIGIDA AQUI!

import com.dio.labpadroesjava.delivery.config.DeliverySystemConfig;
import com.dio.labpadroesjava.delivery.facade.OrderProcessingFacade;
import com.dio.labpadroesjava.delivery.model.Order;
import com.dio.labpadroesjava.delivery.model.Order.OrderBuilder; // Importa o Builder

public class Main {
        public static void main(String[] args) {
                // --- Demonstração do Singleton ---
                System.out.println("--- Testando Singleton (Configuração do Sistema) ---");
                DeliverySystemConfig config1 = DeliverySystemConfig.getInstance();
                DeliverySystemConfig config2 = DeliverySystemConfig.getInstance();

                System.out.println("As instâncias de configuração são as mesmas? " + (config1 == config2));
                System.out.println("Nome do sistema: " + config1.getSystemName());
                config1.setStandardDeliveryCostPerKm(0.65); // Alterando a configuração via uma instância
                System.out.println("Novo custo padrão por Km (via config2): " + config2.getStandardDeliveryCostPerKm());

                // --- Demonstração do Facade, Strategy, Builder e Observer ---
                OrderProcessingFacade orderFacade = new OrderProcessingFacade();

                // Criando pedidos usando o padrão Builder
                System.out.println("\n--- Criando pedidos com o Padrão Builder ---");
                Order order1 = new OrderBuilder("ORD001")
                                .withCustomerName("Alice Silva")
                                .withCustomerAddress("Rua A, 123 - Cidade X")
                                .withTotalAmount(150.00)
                                .withDeliveryDistanceKm(10.0)
                                .build();

                Order order2 = new OrderBuilder("ORD002")
                                .withCustomerName("Bob Souza")
                                .withCustomerAddress("Av. B, 45 - Cidade Y")
                                .withTotalAmount(80.50)
                                .withDeliveryDistanceKm(2.5)
                                .build();

                Order order3 = new OrderBuilder("ORD003")
                                .withCustomerName("Carlos Mendes")
                                .withCustomerAddress("Rua C, 789 - Cidade Z (Retirada)")
                                .withTotalAmount(220.00)
                                .withDeliveryDistanceKm(0.0) // 0km para retirada
                                .build();

                // Processando pedidos com diferentes estratégias e observando as notificações
                System.out.println("\n--- Processando Pedidos com Facade, Strategy e Observer ---");
                orderFacade.processOrder(order1, "standard");
                orderFacade.processOrder(order2, "express");
                orderFacade.processOrder(order3, "pickup");

                // Tentativa com tipo de entrega inválido
                Order order4 = new OrderBuilder("ORD004")
                                .withCustomerName("Diana Lima")
                                .withCustomerAddress("Travessa D, 10 - Cidade W")
                                .withTotalAmount(75.00)
                                .withDeliveryDistanceKm(5.0)
                                .build();
                orderFacade.processOrder(order4, "fastest");
        }
}