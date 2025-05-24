Sistema de Gerenciamento de Entrega de Pedidos (JavaFX)
Este projeto é uma aplicação desktop desenvolvida em JavaFX que simula um sistema simplificado de gerenciamento e processamento de pedidos de entrega. Ele foi criado com o propósito de demonstrar a aplicação prática de diversos padrões de projeto de software em um contexto de negócio real.

🚀 Funcionalidades Principais
Registro de Pedidos: Interface para inserir detalhes do pedido, como ID, nome e endereço do cliente, valor total e distância de entrega.
Seleção de Tipo de Entrega: Permite escolher entre diferentes estratégias de entrega (Ex: Padrão, Expressa, Retirada).
Processamento de Pedidos: Simula o fluxo de processamento de um pedido, aplicando lógicas específicas baseadas no tipo de entrega.
Configuração Dinâmica: Demonstra a configuração de parâmetros do sistema (como custo de entrega por KM) em tempo de execução.
Log de Atividades: Exibe um log detalhado das ações e processamentos na própria interface.

💡 Padrões de Projeto Implementados
Este projeto serve como um excelente exemplo da aplicação dos seguintes padrões de projeto:

Singleton: Utilizado para garantir que haja apenas uma instância da DeliverySystemConfig, gerenciando configurações globais do sistema, como o custo de entrega padrão.
Strategy: Empregado para definir diferentes algoritmos de cálculo de entrega (e.g., StandardDelivery, ExpressDelivery, PickupDelivery), permitindo que o comportamento da entrega seja selecionado em tempo de execução sem alterar o código principal.
Facade: A classe OrderProcessingFacade simplifica a interface para o subsistema de processamento de pedidos, ocultando a complexidade de diversas operações internas (como cálculo, notificação e atualização de logística) para o cliente (a UI).

Builder: O padrão OrderBuilder é usado para construir objetos Order de forma mais flexível e legível, separando a construção da representação complexa do objeto.
Observer: Implementado para notificar diferentes partes do sistema (como CustomerNotifier e LogisticsUpdater) sobre mudanças no status do pedido, desacoplando os objetos que monitoram dos objetos que são monitorados.

🛠️ Tecnologias Utilizadas:

Java 17+

JavaFX 22 (para a interface gráfica)

Maven (para gerenciamento de dependências e build do projeto)



📈 Melhorias Futuras (Sugestões)
Persistência de Dados: Adicionar um banco de dados (SQLite, H2, etc.) para persistir informações de pedidos e configurações.
Validação de Entrada: Implementar validações de entrada de dados mais robustas na UI.
Mais Estratégias de Entrega: Criar novos tipos de entrega com lógicas de cálculo mais complexas.
Interface de Configuração Dedicada: Desenvolver uma tela de configuração mais completa para o Singleton.
Testes Unitários: Adicionar testes unitários para a lógica de negócio e os padrões de projeto.
Documentação Javadoc: Adicionar documentação Javadoc para classes e métodos importantes.
