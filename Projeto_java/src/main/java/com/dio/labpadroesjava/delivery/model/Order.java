package com.dio.labpadroesjava.delivery.model;

import com.dio.labpadroesjava.delivery.strategy.DeliveryStrategy;
import com.dio.labpadroesjava.delivery.observer.OrderObserver;

import java.util.ArrayList;
import java.util.List;

// Padrões: Builder (classe aninhada) e Observer (Subject/Observable)
// Representa um pedido no sistema.
public class Order {
    private String orderId;
    private String customerName;
    private String customerAddress;
    private double totalAmount;
    private double deliveryDistanceKm;
    private double deliveryCost;
    private DeliveryStrategy deliveryStrategy;
    private String status;

    private List<OrderObserver> observers = new ArrayList<>(); // Lista de observadores

    // Construtor privado para ser usado exclusivamente pelo Builder
    private Order(String orderId, String customerName, String customerAddress, double totalAmount,
            double deliveryDistanceKm) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.totalAmount = totalAmount;
        this.deliveryDistanceKm = deliveryDistanceKm;
        this.status = "PENDING"; // Status inicial
    }

    // Métodos para gerenciar observadores
    public void addObserver(OrderObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        this.observers.remove(observer);
    }

    // Notifica todos os observadores
    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }

    // Setter para o status que notifica os observadores
    public void setStatus(String status) {
        this.status = status;
        System.out.println("Status do Pedido " + orderId + " alterado para: " + status);
        notifyObservers(); // Notifica os observadores sempre que o status muda
    }

    // Getters e Setters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getDeliveryDistanceKm() {
        return deliveryDistanceKm;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public DeliveryStrategy getDeliveryStrategy() {
        return deliveryStrategy;
    }

    public void setDeliveryStrategy(DeliveryStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", totalAmount=" + totalAmount +
                ", deliveryDistanceKm=" + deliveryDistanceKm +
                ", deliveryCost=" + String.format("%.2f", deliveryCost) +
                ", deliveryType=" + (deliveryStrategy != null ? deliveryStrategy.getClass().getSimpleName() : "N/A") +
                ", status='" + status + '\'' +
                '}';
    }

    // --- Início da classe Builder (interna estática) ---
    // Padrão: Builder
    public static class OrderBuilder {
        private String orderId;
        private String customerName;
        private String customerAddress;
        private double totalAmount;
        private double deliveryDistanceKm;

        public OrderBuilder(String orderId) { // ID do pedido é obrigatório
            this.orderId = orderId;
        }

        public OrderBuilder withCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public OrderBuilder withCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
            return this;
        }

        public OrderBuilder withTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public OrderBuilder withDeliveryDistanceKm(double deliveryDistanceKm) {
            this.deliveryDistanceKm = deliveryDistanceKm;
            return this;
        }

        public Order build() {
            // Validações básicas podem ser adicionadas aqui
            if (this.orderId == null || this.customerName == null || this.totalAmount <= 0) {
                throw new IllegalStateException("Order ID, Customer Name and Total Amount are required.");
            }
            return new Order(orderId, customerName, customerAddress, totalAmount, deliveryDistanceKm);
        }
    }
    // --- Fim da classe Builder ---
}