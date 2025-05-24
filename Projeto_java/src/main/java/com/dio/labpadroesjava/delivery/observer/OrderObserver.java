package com.dio.labpadroesjava.delivery.observer;

import com.dio.labpadroesjava.delivery.model.Order;

// Padrão: Observer (Interface do Observador)
public interface OrderObserver {
    void update(Order order);
}