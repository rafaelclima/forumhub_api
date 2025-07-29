# 🧠 ForumHub API

[![Java](https://img.shields.io/badge/Java-21-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-green?logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Dockerized-blue?logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

🚀 Projeto desenvolvido como desafio final do curso **Spring Boot da Alura**.

---

## 📌 Sobre o Projeto

A **ForumHub API** é uma aplicação RESTful desenvolvida com Spring Boot que simula o backend de um fórum de discussões. Nela, é possível:

- ✅ Cadastrar, listar, atualizar e remover tópicos.
- 🔐 Autenticar usuários com JWT (JSON Web Tokens).
- 🛡️ Proteger endpoints com Spring Security.
- 💬 Associar tópicos, respostas e usuários.

---

## 🧱 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5+
- Spring Security
- Spring Data JPA
- PostgreSQL (via Docker)
- JWT (com `oauth2-resource-server`)
- Lombok
- Bean Validation

---

## 🐘 Banco de Dados

O banco de dados PostgreSQL está sendo executado em um container Docker usando a imagem oficial da Bitnami.

### 🔧 Comando para subir o container:

```bash
docker run -d \
  --name pg-forumhub \
  -e POSTGRESQL_USERNAME=admin \
  -e POSTGRESQL_PASSWORD=admin \
  -e POSTGRESQL_DATABASE=forumhub \
  -p 5432:5432 \
  bitnami/postgresql:latest
```

### 🔗 Configuração no `application-prod.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/forumhub
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update #em prod use validate
api.security.token.secret=${JWT_SECRET}
```

---

## 📂 Como Executar o Projeto

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/forumhub-api.git
cd forumhub-api
```

2. Crie um arquivo `.env` (ou configure suas variáveis de ambiente) com o valor do `JWT_SECRET`.

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

---

## ✅ Endpoints principais

| Método | Rota                 | Descrição                             |
|--------|----------------------|---------------------------------------|
| POST   | `/api/login`                  | Autenticação de usuários     |
| POST   | `/api/topicos`                | Cria novo tópico             |
| GET    | `/api/topicos`                | Lista todos os tópicos       |
| PUT    | `/api/topicos/{id}`           | Atualiza um tópico           |
| DELETE | `/api/topicos/{id}`           | Remove um tópico             |
| POST   | `/api/topicos/{id}/respostas` | Remove um tópico             |

> Endpoints protegidos requerem um token JWT válido no header:  
> `Authorization: Bearer SEU_TOKEN_AQUI`

---

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou pull request.

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Feito com 💚 por Rafael Lima — [@rafaelclima](https://github.com/rafaelclima)
