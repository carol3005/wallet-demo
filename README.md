# Aplicativo de demonstração de carteira digital

Este projeto é um aplicativo de Carteira Digital baseado em microsserviços que oferece suporte a diversas transações, como adição de fundos, retiradas, compras, cancelamentos e reembolsos.

## Características

- **API REST** com endpoints para:
  - Criando uma carteira
  - Verificação de saldos
  - Visualização de extratos de transação
  - Adicionando fundos
  - Retirada de fundos
  - Fazer compras
  - Cancelamento de transações
  - Reembolso de transações
- **Swagger** para documentação da API
- **Banco de dados H2** com persistência de dados
- **Migrações de banco de dados** usando Flyway
- **Testes unitários** para métodos de serviço
- **Dockerizado** para fácil implantação

## Pré-requisitos

- Java 11 ou superior
- Maven
- Docker (para conteinerização)

## Começando

### Clonar o repositório

```bash
git clone https://github.com/carol3005/wallet-demo.git
cd wallet-demo
```

### Construa o Projeto

```bash
mvn clean install
```

### Execute o aplicativo

```bash
mvn spring-boot:run
```

### Acessando o Aplicativo
- UI do Swagger: http://localhost:8081/swagger-ui.html
- Console H2: http://localhost:8081/h2-console
- URL JDBC: jdbc:h2:file:./data/demodb
  - Nome de usuário: demonstração
  - Senha: (deixe em branco)

## Terminais de API
### Gerenciamento de carteira
- Criar carteira
- `POST /api/carteira`
- Verifique saldo e transações
- `GET /api/carteira/{walletId}/transações`

### Transações
- Adicionar fundos
  - `POST /api/carteira/{walletId}/add`
  - Parâmetros: valor (por exemplo, ?amount=100)

- Retirar fundos
  - `POST /api/carteira/{walletId}/retirada`
  - Parâmetros: valor (por exemplo, ?amount=50)

- Fazer compra
  - `POST /api/carteira/{walletId}/compra`
  - Parâmetros: valor, descrição (por exemplo, ?amount=40&description=Comprar item)

- Cancelar transações
  - `POST /api/wallet/{walletId}/cancel/{transactionId}`

- Transação de reembolso
  - `POST /api/wallet/{walletId}/refund/{transactionId}`

### Migrações de banco de dados
As migrações são gerenciadas pelo Flyway e localizadas em `src/main/resources/db/migration`.

### Executando testes
Os testes unitários estão localizados em `src/test/java/com/test/wallet_demo`. Execute-os com:

```bash
mvn test
```

## Implantação do Docker
Para construir e executar o contêiner Docker:

```bash
docker build -t wallet-demo.
docker run -p 8080:8080 wallet-demo
```

## Projeto de arquitetura

```bash
+---------------------+
| Gateway da API      |
+---------------------+
 |
 v
+---------------------+
| Serviço de carteira |
| (Camada API REST)   |
+---------------------+
 |
 v
+-----------------------+
| Camada de serviço     |
| - Lógica de Negócios  |
+-----------------------+
 |
 v
+-----------------------------+
| Camada de repositório       |
| - Repositório de carteira   |
| - Repositório de transações |
+-----------------------------+
 |
 v
+---------------------+
| Banco de dados H2   |
| - Wallet            |
| - Transactions      |
+---------------------+
```

A aplicação segue uma arquitetura de microsserviços com os seguintes componentes:
- **Camada do controlador**: Lida com solicitações e respostas HTTP.
- **Camada de serviço**: Contém lógica de negócios.
- **Camada de Repositório**: Interface com o banco de dados.
- **Camada Modelo**: Representa as entidades do domínio.

## Melhorias Futuras
- Testes de integração para testes ponta a ponta.
- Use uma forma de banco de dados mais confiável (por exemplo, MySQL, PostgresSQL, Oracle)
- Tratamento e validação de erros aprimorados.
- Adicionar um novo modelo ajuda na autenticação (ex.: Usuário com senha)
- Suporte para tipos de transações adicionais.