# Novo Anjo - Api de ajuda humanitaria

Api que tem como objetivo ajudar pessoas, seja com alimentos, prestação de serviço etc.

---
### Stacks

* Java 17
* Spring boot 2.6.4
  * Actuator
* Maven
* Swagger (Api Docs)
* Mysql
* Flyway
* Lombok
* Docker

### Configurações para desenvolvedores

* Java 17
* Docker

### Como rodar localmente

docker-compose up -d /devops/local_env/

### Endereços LOCAL

    NovoAnjoApi: http://localhost:8080/
    Swagger: http://localhost:8080/swagger-ui.html

### ToDo

* Escrever teste unitario / Integração
    . Configurar banco h2 
    . criar tabelas
    . Criar script inicial DB
* Criar exchedule eventos pendentes alerta master
* Mudar valores para Variavel de ambiente
* Configurar Autenticação
* Configurar Swagger
* Configurar CI/CD
