# 📚 Literalura API

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](LICENSE)
[![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

> 🔐 API segura baseada em Spring Boot 3, JWT e PostgreSQL para gerenciamento de livros e autores da literatura mundial.

---

## ✨ Sobre o projeto

O **Literalura** é uma API RESTful criada como desafio final do curso de Spring Boot da [Alura](https://www.alura.com.br). O objetivo é permitir o cadastro e consulta de obras literárias, com foco em autores, idiomas e rankings baseados em dados reais da [Gutenberg Project](https://www.gutenberg.org/).

---

## 🚀 Tecnologias utilizadas

- ✅ **Java 21**
- ✅ **Spring Boot 3.2**
- ✅ **Spring Data JPA**
- ✅ **Spring Security com JWT**
- ✅ **Bean Validation**
- ✅ **PostgreSQL**
- ✅ **Lombok**
- ✅ **Maven**

---

## 🔐 Segurança

A autenticação e autorização da API são baseadas em **JWT (JSON Web Token)**, garantindo uma abordagem **stateless** para maior escalabilidade e segurança.

Endpoints protegidos exigem um token válido no header da requisição:

```http
Authorization: Bearer seu_token_aqui
```

---

## 🛠️ Como rodar o projeto localmente

1. ✅ Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/literalura.git
   cd literalura
   ```

2. ✅ Crie o banco de dados:
   ```sql
   CREATE DATABASE literalura;
   ```

3. ✅ Configure o `application.properties` ou `application.yml` com suas credenciais:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

4. ✅ Rode o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 📬 Endpoints principais

| Método | Endpoint            | Descrição                      |
|--------|---------------------|--------------------------------|
| POST   | `/login`            | Autenticação com usuário/senha |
| POST   | `/livros`           | Cadastra novo livro            |
| GET    | `/livros`           | Lista todos os livros          |
| GET    | `/autores`          | Lista todos os autores         |
| GET    | `/livros/{id}`      | Consulta livro por ID          |
| DELETE | `/livros/{id}`      | Remove um livro                |

> ℹ️ A documentação completa da API (Swagger) estará disponível em breve.

---

## 🧪 Testes

Execute os testes com:

```bash
./mvnw test
```

---

## 🧑‍💻 Autor

Desenvolvido com ❤️ por **Rafael Lima**

- 🔗 [LinkedIn](https://www.linkedin.com/in/rafael-lima-dev)
- 💻 [Portfólio](https://rafael.dev.br) (se houver)

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.