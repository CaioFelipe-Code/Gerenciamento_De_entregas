package com.dio.labpadroesjava.delivery;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent; // Adicione este import se você for usar 'ActionEvent event'

import com.dio.labpadroesjava.delivery.config.DeliverySystemConfig;
import com.dio.labpadroesjava.delivery.facade.OrderProcessingFacade;
import com.dio.labpadroesjava.delivery.model.Order;
import com.dio.labpadroesjava.delivery.model.Order.OrderBuilder;
import com.dio.labpadroesjava.delivery.observer.CustomerNotifier;
import com.dio.labpadroesjava.delivery.observer.LogisticsUpdater;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class DeliveryAppController {

    // --- Elementos da UI mapeados do FXML ---
    @FXML
    private TextField orderIdField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField totalAmountField;
    @FXML
    private TextField deliveryDistanceField;
    @FXML
    private ComboBox<String> deliveryTypeComboBox;
    @FXML
    private TextArea outputTextArea; // Área para exibir logs e resultados
    @FXML
    private Label singletonStatusLabel; // Para mostrar status do Singleton
    @FXML
    private Label configCostLabel; // Para mostrar o custo de entrega

    // --- Instâncias da Lógica de Negócio ---
    private OrderProcessingFacade orderFacade;
    private DeliverySystemConfig systemConfig;

    // --- Método chamado automaticamente após o FXML ser carregado ---
    @FXML
    public void initialize() {
        // Inicializa a lógica de negócio (seus Singletons, Facades, etc.)
        systemConfig = DeliverySystemConfig.getInstance();
        orderFacade = new OrderProcessingFacade();

        // Configura o ComboBox com os tipos de entrega
        deliveryTypeComboBox.getItems().addAll("standard", "express", "pickup");
        deliveryTypeComboBox.getSelectionModel().selectFirst(); // Seleciona "standard" por padrão

        // Redireciona System.out para o TextArea
        // Isso é crucial para ver as mensagens dos seus métodos existentes na GUI
        System.setOut(new PrintStream(new ConsoleOutputStream(outputTextArea)));
        System.setErr(new PrintStream(new ConsoleOutputStream(outputTextArea))); // Também para erros

        // --- Demonstração do Singleton na GUI ---
        updateSingletonStatus();

        // Mensagens de boas-vindas na área de saída
        outputTextArea.appendText("Sistema de Entrega de Pedidos iniciado!\n");
        outputTextArea.appendText("Versão do Java: " + System.getProperty("java.version") + "\n");
        outputTextArea.appendText("---------------------------------------------\n");
    }

    // Método para atualizar o status do Singleton na GUI
    private void updateSingletonStatus() {
        // Demonstração do Singleton na UI
        DeliverySystemConfig checkConfig1 = DeliverySystemConfig.getInstance();
        DeliverySystemConfig checkConfig2 = DeliverySystemConfig.getInstance();
        boolean sameInstance = (checkConfig1 == checkConfig2);
        singletonStatusLabel.setText("Singleton (Configuração): " + (sameInstance ? "Instância Única" : "Erro!") +
                " | Sistema: " + systemConfig.getSystemName());
        configCostLabel.setText(String.format("Custo Padrão/Km: R$ %.2f", systemConfig.getStandardDeliveryCostPerKm()));
    }

    // --- Método acionado pelo botão "Processar Pedido" ---
    @FXML
    private void processOrder() { // Este já estava private, mas é comum ver public aqui
        outputTextArea.appendText("\n--- Processando Novo Pedido --- \n");
        try {
            // 1. Coleta os dados da UI
            String orderId = orderIdField.getText();
            String customerName = customerNameField.getText();
            String customerAddress = customerAddressField.getText();
            double totalAmount = Double.parseDouble(totalAmountField.getText());
            double deliveryDistance = Double.parseDouble(deliveryDistanceField.getText());
            String deliveryType = deliveryTypeComboBox.getValue();

            // 2. Validação básica (pode ser expandida)
            if (orderId.isEmpty() || customerName.isEmpty() || customerAddress.isEmpty() || deliveryType == null) {
                showAlert(Alert.AlertType.ERROR, "Erro de Entrada",
                        "Todos os campos de texto e tipo de entrega são obrigatórios.");
                outputTextArea.appendText("Erro: Campos obrigatórios não preenchidos.\n");
                return;
            }

            // 3. Cria o objeto Order usando o Builder
            Order order = new OrderBuilder(orderId)
                    .withCustomerName(customerName)
                    .withCustomerAddress(customerAddress)
                    .withTotalAmount(totalAmount)
                    .withDeliveryDistanceKm(deliveryDistance)
                    .build();

            // 4. Chama a lógica de negócio através do Facade
            outputTextArea.appendText("Chamando Facade para pedido: " + orderId + ", Tipo: " + deliveryType + "\n");
            orderFacade.processOrder(order, deliveryType);
            outputTextArea.appendText("Pedido " + orderId + " processado com sucesso!\n");

            // Limpa os campos após o processamento (opcional)
            clearFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Entrada",
                    "Valores para Total do Pedido ou Distância de Entrega devem ser números válidos.");
            outputTextArea.appendText("Erro: Formato de número inválido. " + e.getMessage() + "\n");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro no Processamento",
                    "Ocorreu um erro ao processar o pedido: " + e.getMessage());
            e.printStackTrace(System.err); // Imprime o stack trace no TextArea
        }
    }

    // Método acionado pelo botão "Configurações" (exemplo de como manipular o
    // Singleton)
    @FXML
    private void openConfigDialog() { // Este também está private, mas é comum ver public
        TextInputDialog dialog = new TextInputDialog(String.valueOf(systemConfig.getStandardDeliveryCostPerKm()));
        dialog.setTitle("Configurar Custo de Entrega Padrão");
        dialog.setHeaderText("Ajustar Custo por Km");
        dialog.setContentText("Novo custo por Km:");

        dialog.showAndWait().ifPresent(result -> {
            try {
                double newCost = Double.parseDouble(result);
                systemConfig.setStandardDeliveryCostPerKm(newCost);
                showAlert(Alert.AlertType.INFORMATION, "Configuração",
                        "Custo de entrega atualizado para: R$ " + String.format("%.2f", newCost));
                outputTextArea.appendText("Configuração: Custo padrão por Km atualizado para R$ "
                        + String.format("%.2f", newCost) + "\n");
                updateSingletonStatus(); // Atualiza a label na GUI
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Por favor, insira um número válido para o custo.");
                outputTextArea.appendText("Erro: Custo inválido. " + e.getMessage() + "\n");
            }
        });
    }

    // --- Métodos Auxiliares ---
    @FXML // Boa prática, embora não estritamente necessário para métodos públicos
          // chamados via onAction
    public void clearFields() { // <--- MUDE DE 'private' PARA 'public' AQUI!
        orderIdField.clear();
        customerNameField.clear();
        customerAddressField.clear();
        totalAmountField.clear();
        deliveryDistanceField.clear();
        deliveryTypeComboBox.getSelectionModel().selectFirst();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // --- Classe Interna para Redirecionar System.out para o TextArea ---
    // (Isso é uma simplificação, para uso em produção, considere uma lib de
    // logging)
    private static class ConsoleOutputStream extends java.io.OutputStream {
        private TextArea textArea;
        private StringBuilder buffer = new StringBuilder();

        public ConsoleOutputStream(TextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            char c = (char) b;
            buffer.append(c);
            if (c == '\n') {
                flush();
            }
        }

        @Override
        public void flush() {
            // Garante que a atualização da UI ocorra na JavaFX Application Thread
            javafx.application.Platform.runLater(() -> {
                textArea.appendText(buffer.toString());
                buffer.setLength(0); // Limpa o buffer
            });
        }
    }
}