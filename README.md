# PB Springboot - Desafio 3: Projeto Banco Americano

## Descrição

Este projeto é composto por 3 Micro Serviços que se comunicam entre si, formando entao um pequeno sistema bancario.
Ao realizar um pagamento atraves do MsPayment, ele ira se comunicar com o MsCalculate, este que por sua vez ira realizar o calculo de pontos
e entao mandar uma mensagem para o MsCustomer, que ira salvar os pontos adquiridos na compra realizada pelo respectivo cliente.

## MSCUSTOMER

- **Cadastro de Clientes:** CPF, name, gender, birthday, email e foto em base64.
- **Banco de Dados Relacional:** RDS MySql da AWS.
- **Recebimento de Mensagens:** Via RabbitMq, recebe mensagens postadas pelo MsCalculate
- **Edição do Cadastro de Clientes:** Resgatar cliente por id, deletar e atualizar.
- **Documentação da API:** Utilizando Swagger.

### Jacoco
![Jacoco MsCustomer](https://minhabucketparaojava.s3.amazonaws.com/imagens-de-perfil/jacocoMSCUSTOMER.PNG)

## MSCALCULATE

- **Cadastro de Rules:** Category e parity.
- **Banco de Dados Relacional:** RDS MySql da AWS.
- **Lançamento de Mensagens:** Via RabbitMq, manda mensagens que seram consumidas pelo MsCustomer
- **Edição de Rules:** Resgatar todas as Rules, deletar e atualizar.
- **Mensagem para o MsCustomer:** atraves do endpoint /calculate, é possivel calcular os pontos e enviar para serem salvos pelo MsCustomer
- **Documentação da API:** Utilizando Swagger.

### Jacoco
![Jacoco MsCalculate](https://minhabucketparaojava.s3.amazonaws.com/imagens-de-perfil/jacocoMsCalculate.PNG)

## MSPAYMENT

- **Realização de Pagamentos:** customerid, categoryid, total.
- **Banco de Dados Relacional:** RDS MySql da AWS.
- **Comunicação:** utilizando o OpenFeign, o MSPayment se comunica com o MSCustomer e o MSCalculate via HTTP, realizando request POST e GET.
- **Gerenciamento de Pagamentos:** Resgatar pagamento por id UUID e resgatar pagamento por id do Customer.
- **Automatização:** ao realizar um pagamento, o mspayment realiza um post para o mscalculate que automaticamente ja calcula os pontos e ja manda uma mensagem via rabbitmq para o mscustomer que ja salva a quantidade de pontos.
- **Documentação da API:** Utilizando Swagger.

### Jacoco
![Jacoco MsPayment](https://minhabucketparaojava.s3.amazonaws.com/imagens-de-perfil/jacocoMsPayment.PNG)


## Tecnologias Utilizadas nos Ms

- Java
- Spring Boot
- MySql RDS AWS
- Jacoco
- OpenFeign
- RabbitMQ
- Swagger

## Configuração e Execução

### Pré-requisitos

- Java JDK 21+
- Maven

### Passos

1. **Clone o repositório:**
   ```sh
   git clone https://github.com/raiiguilherme/PbAbrilDes3_RaiGuilherme.git
   ```

2. **Configurações:**
   faça a alteração do application.properties com sua credenciais, abra o link do swagger e aproveite!
