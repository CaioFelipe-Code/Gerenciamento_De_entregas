package com.dio.labpadroesjava.delivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL; // Importe esta classe também, se não estiver já importada

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carrega o arquivo FXML que define a interface
        URL fxmlLocation = getClass().getResource("/com/dio/labpadroesjava/delivery/delivery-app-view.fxml");
        System.out.println("FXML Location: " + fxmlLocation); // <--- LINHA DE DEPURACAO ADICIONADA

        if (fxmlLocation == null) {
            throw new IOException("FXML file not found at: /com/dio/labpadroesjava/delivery/delivery-app-view.fxml");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load(); // Esta é a linha 19 agora, ou próxima a isso no seu arquivo

        // Define um controlador para o FXML (o JavaFX faz isso automaticamente se o
        // fxml:controller estiver setado no FXML)
        // DeliveryAppController controller = loader.getController(); // Pode pegar a
        // instância do controller aqui se precisar

        Scene scene = new Scene(root); // Cria a cena com a UI carregada

        // Adiciona o arquivo CSS para estilização
        // Verifique se 'styles.css' está na mesma pasta de recursos que
        // 'delivery-app-view.fxml'
        URL cssLocation = getClass().getResource("/com/dio/labpadroesjava/delivery/styles.css");
        if (cssLocation != null) {
            scene.getStylesheets().add(cssLocation.toExternalForm());
        } else {
            System.err.println("WARNING: CSS file not found at: /com/dio/labpadroesjava/delivery/styles.css");
        }

        primaryStage.setTitle("Sistema de Entrega de Pedidos (JavaFX)"); // Título da janela
        primaryStage.setScene(scene); // Define a cena na janela
        primaryStage.show(); // Exibe a janela
    }

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }
}