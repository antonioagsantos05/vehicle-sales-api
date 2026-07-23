# Vehicle Sales API

API transacional para cadastro, edição, listagem e venda de veículos. Este repositório não cadastra usuários nem armazena credenciais: ele valida JWTs emitidos pelo serviço separado `vehicle-sales-auth` e registra somente o claim `sub` como identificador do comprador.

## Tecnologias
Java 21, Spring Boot 3, Spring Security OAuth2 Resource Server, Spring Data JPA, PostgreSQL, Flyway, Swagger/OpenAPI, Docker e GitHub Actions.

## Executar localmente
1. Inicie primeiro o repositório `vehicle-sales-auth` na porta 8081.
2. Execute:
```bash
docker compose up --build
```
3. Swagger: http://localhost:8080/swagger-ui.html
4. Health: http://localhost:8080/actuator/health

## Segurança
- `ADMIN`: cadastrar e editar veículos.
- `BUYER`: comprar veículos e consultar suas vendas.
- O comprador é identificado pelo claim JWT `sub`.

## Endpoints principais
- `POST /api/v1/vehicles`
- `PUT /api/v1/vehicles/{id}`
- `GET /api/v1/vehicles/available`
- `GET /api/v1/vehicles/sold`
- `POST /api/v1/vehicles/{id}/purchase`
- `GET /api/v1/sales/me`

## Testes
```bash
mvn clean verify
```

## Variáveis de ambiente
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_ISSUER_URI`
- `JWT_JWK_SET_URI`
- `KEYCLOAK_EXTERNAL_URL`

## CI/CD
- `ci.yml`: compila e testa em Pull Requests e pushes na `main`.
- `deploy.yml`: gera e publica a imagem Docker no GitHub Container Registry após merge na `main`.
