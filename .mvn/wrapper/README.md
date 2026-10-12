# Baozi Store — API REST

Trabalho acadêmico da disciplina Desenvolvimento Web Back-End.

A aplicação permite cadastrar, listar, consultar por identificador
e excluir clientes, produtos e pedidos de uma loja de pão chinês.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- MySQL
- Maven
- Postman para testes

## Organização do projeto

- model: entidades Cliente, Produto e Pedido.
- repository: interfaces de acesso ao banco de dados.
- controller: endpoints REST.
- BaoziStoreApplication: classe que inicia a aplicação.

## Como executar no Eclipse

1. Baixe o projeto e extraia o ZIP.
2. No Eclipse, escolha File → Import → Maven → Existing Maven Projects.
3. Selecione a pasta que contém pom.xml e conclua a importação.
4. Configure o projeto para utilizar o JDK 21.
5. Com o MySQL em execução, crie o banco:

```sql
CREATE DATABASE IF NOT EXISTS baozi_store
CHARACTER SET utf8mb4;
```

6. Confira a conexão em src/main/resources/application.properties.
   A configuração padrão usa localhost, porta 3306 e banco baozi_store.
7. Em Run → Run Configurations → Java Application, selecione
   BaoziStoreApplication como classe principal.
8. Na aba Environment, adicione:
   - DB_USER: seu usuário do MySQL.
   - DB_PASSWORD: sua senha do MySQL.
9. Execute a aplicação.

A API fica disponível em http://localhost:8080.

## Endpoints

| Método | Endereço | Operação |
|---|---|---|
| POST | /clientes | Cadastrar cliente |
| GET | /clientes | Listar clientes |
| GET | /clientes/{id} | Consultar cliente por ID |
| DELETE | /clientes/{id} | Excluir cliente |
| POST | /produtos | Cadastrar produto |
| GET | /produtos | Listar produtos |
| GET | /produtos/{id} | Consultar produto por ID |
| DELETE | /produtos/{id} | Excluir produto |
| POST | /pedidos | Cadastrar pedido |
| GET | /pedidos | Listar pedidos |
| GET | /pedidos/{id} | Consultar pedido por ID |
| DELETE | /pedidos/{id} | Excluir pedido |

## Exemplo de cadastro de cliente

POST /clientes

```json
{
  "nome": "Eduarda5563930",
  "clienteDesde": "2026-10-12"
}
```

## Exemplo de cadastro de produto

POST /produtos

```json
{
  "nome": "Baozi de carne",
  "preco": 8.50,
  "estoque": true
}
```

## Exemplo de cadastro de pedido

POST /pedidos

```json
{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 3
}
```

No pedido, substitua clienteId e produtoId pelos identificadores
retornados nos cadastros anteriores.

## Regras de cadastro de pedidos

- Cliente, produto e quantidade devem ser informados.
- A quantidade deve ser maior que zero.
- O cliente e o produto precisam existir.
- Os identificadores são armazenados como campos Long.
- A existência do cliente e do produto é verificada pelo controller
  no momento do cadastro; não há chaves estrangeiras configuradas
  nas entidades.

## Respostas HTTP

- 200 OK: consulta realizada.
- 201 Created: cadastro realizado.
- 204 No Content: exclusão realizada.
- 400 Bad Request: dados inválidos no cadastro de pedido.
- 404 Not Found: registro não encontrado na consulta por ID
  ou na exclusão.
