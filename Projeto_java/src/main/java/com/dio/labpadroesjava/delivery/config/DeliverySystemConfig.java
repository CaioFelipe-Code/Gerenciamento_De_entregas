package com.dio.labpadroesjava.delivery.config;

// Padrão: Singleton
// Garante que haja apenas uma instância da configuração do sistema de entrega.
public class DeliverySystemConfig {

    private static DeliverySystemConfig instance;
    private String systemName;
    private double standardDeliveryCostPerKm;

    // Construtor privado para impedir instancição externa.
    private DeliverySystemConfig() {
        this.systemName = "DIO Delivery Service";
        this.standardDeliveryCostPerKm = 0.50; // Exemplo de custo
        System.out.println("Configuração do sistema de entrega inicializada.");
    }

    // Método estático para obter a única instância.
    public static DeliverySystemConfig getInstance() {
        if (instance == null) {
            instance = new DeliverySystemConfig();
        }
        return instance;
    }

    public String getSystemName() {
        return systemName;
    }

    public double getStandardDeliveryCostPerKm() {
        return standardDeliveryCostPerKm;
    }

    public void setStandardDeliveryCostPerKm(double standardDeliveryCostPerKm) {
        this.standardDeliveryCostPerKm = standardDeliveryCostPerKm;
    }
}