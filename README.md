# 🧠 ForumHub API

[![Java](https://img.shields.io/badge/Java-21-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?logo=spring)](https://spring.io/projects/spring-boot)
[![H2 Database](https://img.shields.io/badge/H2_Database-In--Memory-red?logo=h2)](https://www.h2database.com)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

🚀 Projeto desenvolvido como desafio final do curso **Spring Boot da Oracle One Alura**, construindo uma API RESTful completa para um fórum de discussões.

---

## 📌 Sobre o Projeto

O **ForumHub API** é uma aplicação RESTful que simula o backend de um fórum de discussões, permitindo que os usuários interajam com tópicos e respostas de forma segura.

- ✅ **Gerenciamento de Usuários:** Cadastro e autenticação de usuários com JWT.
- ✅ **Gerenciamento de Tópicos:** CRUD completo (Criar, Listar, Detalhar, Atualizar, Deletar) para tópicos.
- ✅ **Sistema de Respostas:** Permite adicionar respostas a tópicos e marcar uma delas como a solução.
- 🔐 **Segurança Robusta:** Proteção de endpoints baseada em roles (USER, ADMIN) com Spring Security.
- 🛡️ **Regras de Negócio:** Validações para prevenir tópicos duplicados e regras de autorização para garantir que apenas o autor do tópico ou um administrador possam modificá-lo.

---

## 🧱 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.x**
- **Spring Security:** Para autenticação e autorização com JWT.
- **Spring Data JPA:** Para persistência de dados.
- **H2 Database:** Banco de dados em memória para ambiente de desenvolvimento.
- **JWT (com `oauth2-resource-server`):** Para geração e validação de tokens de acesso usando chaves RSA.
- **Lombok:** Para reduzir código boilerplate.
- **Bean Validation:** Para validação de dados de entrada nos DTOs.
- **SpringDoc OpenAPI:** Para geração de documentação interativa da API.

---

## ⚙️ Configuração Inicial

### Pré-requisitos
- Java 21 (ou superior)
- Maven
- OpenSSL (para geração de chaves)

### 1. Geração das Chaves RSA
A autenticação JWT utiliza um par de chaves RSA (pública e privada). Para gerar os arquivos necessários, execute os seguintes comandos na raiz do projeto:

```bash
# 1. Crie o diretório para armazenar as chaves
mkdir -p src/main/resources/keys

# 2. Gere a chave privada (private.pem)
openssl genrsa -out src/main/resources/keys/private.pem 2048

# 3. Extraia a chave pública a partir da privada (public.pem)
openssl rsa -in src/main/resources/keys/private.pem -pubout -out src/main/resources/keys/public.pem
```
> **Importante:** O diretório `keys` já está incluído no `.gitignore` para garantir que as chaves privadas não sejam enviadas para o repositório.

### 2. Banco de Dados (H2)
O projeto utiliza o banco de dados em memória H2, que é configurado automaticamente pelo Spring Boot. Para visualizar o banco de dados enquanto a aplicação está rodando, habilite o console do H2 no arquivo `src/main/resources/application.properties`:

```properties
# Habilita o console web do H2
spring.h2.console.enabled=true
# Define o caminho para acessar o console
spring.h2.console.path=/h2-console
```
Após iniciar a aplicação, você pode acessar o console em: `http://localhost:8080/h2-console`.
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** (deixe em branco)

---

## 📂 Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/rafaelclima/forumhub_api.git
    cd forumhub_api
    ```

2.  **Execute os passos de Configuração Inicial** (gerar as chaves RSA).

3.  **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```
A API estará disponível em `http://localhost:8080`.

---

## 📖 Documentação da API (Swagger)

Com a aplicação em execução, a documentação interativa da API, gerada pelo SpringDoc, pode ser acessada em:

- **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Nesta interface, você pode visualizar todos os endpoints, seus DTOs de requisição e resposta, e testá-los diretamente.

#### Testando Endpoints Protegidos
1.  Primeiro, use o endpoint `/api/login` para obter um token JWT.
2.  Clique no botão **`Authorize`** no canto superior direito da página do Swagger.
3.  Na janela que abrir, digite `Bearer ` seguido do seu token (ex: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`) e clique em **`Authorize`**.
4.  Agora você pode executar as requisições para os endpoints protegidos.

---

## 🧪 Testando a API com Bruno

O diretório `ForumHub_API/` contém uma coleção de requisições prontas para serem usadas com o cliente de API **[Bruno](https://www.usebruno.com/)**.

Para testar os endpoints:
1.  Abra o Bruno e importe a coleção localizada na pasta `ForumHub_API`.
2.  Execute a requisição **`Login`** primeiro. Ela irá autenticar e salvar o `accessToken` em uma variável de ambiente da coleção.
3.  Execute as outras requisições. Elas usarão automaticamente o token salvo para autenticar nas rotas protegidas.

---

## Endpoints da API

### Autenticação
| Método | Rota         | Descrição                                   | Autenticação |
| :----- | :----------- | :------------------------------------------ | :----------- |
| `POST` | `/api/login` | Autentica um usuário e retorna um token JWT. | Pública      |

### Usuários
| Método | Rota          | Descrição                                   | Autenticação | Autorização |
| :----- | :------------ | :------------------------------------------ | :----------- | :---------- |
| `POST` | `/api/users`  | Cadastra um novo usuário com a role `USER`. | Pública      | -           |
| `GET`  | `/api/users`  | Lista todos os usuários (paginado).         | Requerida    | `ADMIN`     |

### Tópicos e Respostas
| Método   | Rota                        | Descrição                               | Autenticação | Autorização                 |
| :------- | :-------------------------- | :-------------------------------------- | :----------- | :-------------------------- |
| `POST`   | `/api/topico`               | Cria um novo tópico.                    | Requerida    | `USER` ou `ADMIN`           |
| `GET`    | `/api/topico`               | Lista todos os tópicos (paginado).      | Requerida    | `USER` ou `ADMIN`           |
| `GET`    | `/api/topico/{id}`          | Detalha um tópico específico.           | Requerida    | `USER` ou `ADMIN`           |
| `PUT`    | `/api/topico/{id}`          | Atualiza um tópico.                     | Requerida    | Dono do Tópico ou `ADMIN`   |
| `DELETE` | `/api/topico/{id}`          | Exclui um tópico.                       | Requerida    | Dono do Tópico ou `ADMIN`   |
| `POST`   | `/api/topico/{id}/resposta` | Adiciona uma resposta a um tópico.      | Requerida    | `USER` ou `ADMIN`           |
| `PUT`    | `/api/topico/{id}/resposta/{idResposta}` | Marca/desmarca uma resposta como solução. | Requerida    | Dono do Tópico ou `ADMIN`   |

> Endpoints com autenticação requerem um token JWT válido no header:
> `Authorization: Bearer SEU_TOKEN_AQUI`

---

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou pull request.

---

## 📄 Licença

Este projeto está sob a licença MIT.

---

Feito com 💚 por Rafael Lima — [@rafaelclima](https://github.com/rafaelclima)
