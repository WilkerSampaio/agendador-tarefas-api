# 📝Agendador de Tarefas API

API para gerenciamento de tarefas com ntegração via **Feign Client** com a **Usuário API** para criação e gerenciamento de permissões no Spring Security.  

---

## 🛠 Tecnologias Utilizadas

- ☕ **Java 17**  
- 🌱 **Spring Boot**  
- 📦 **Gradle**  
- 🍃 **MongoDB**  
- 🐳 **Docker**  
- 📄 **Swagger (OpenAPI)**  
- 🔐 **JWT** para autenticação e autorização  

---

## 🧭 Funcionalidades Principais

- Criar, listar, atualizar e remover tarefas  
- Integrar com a **API de Usuário** para criar `UserDetails` e gerenciar permissões de acesso  
- Autenticação e autorização via **Spring Security + JWT**  
- Persistência de dados em **MongoDB**  

---

## 📋 Endpoints Principais

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| POST `/tarefas` | Criar nova tarefa | ✅ requer token |
| GET `/tarefas` | Listar todas as tarefas | ✅ requer token |
| GET `/tarefas/{id}` | Buscar tarefa por ID | ✅ requer token |
| PUT `/tarefas/{id}` | Atualizar tarefa existente | ✅ requer token |
| DELETE `/tarefas/{id}` | Deletar tarefa por ID | ✅ requer token |

---

## 🚀 Uso com Docker Compose

Este projeto já está preparado para ser usado apenas com Docker Compose — não é necessário compilar localmente, executar comandos Maven/Gradle, etc.  

### Pré-requisitos

- Docker  
- Docker Compose  

### Passos para subir a aplicação

1. Clone o repositório:  
   ```bash
   git clone https://github.com/WilkerSampaio/agendador-tarefas-api.git
   cd agendador-tarefas-api
   ```

2. Verifique o arquivo `docker-compose.yml`. Ele já contém os serviços necessários:  

   - **api** → aplicação Spring Boot (porta `8081`)  
   - **mongo** → banco de dados MongoDB (porta `27017`)  

   Exemplo de trecho do `docker-compose.yml`:  

   ```yaml
   services:
     api:
       build: .
       container_name: api-container
       ports:
         - "8081:8081"
       environment:
         - MONGO_URI=mongodb://mongo:27017/seu_banco
       depends_on:
         - mongo
       networks:
         - app-network

     mongo:
       image: mongo:latest
       container_name: mongo-container
       ports:
         - "27017:27017"
       networks:
         - app-network

   networks:
     app-network:
       driver: bridge
   ```

3. Suba os containers com o Docker Compose:

   ```bash
   docker-compose up --build
   ```

4. A aplicação estará rodando em:

   ```
   http://localhost:8081
   ```

5. Acesse o Swagger para explorar os endpoints:

   👉 [http://localhost:8081/swagger-ui/index.html#/](http://localhost:8081/swagger-ui/index.html#/)  

---

## 🧱 Integração com Usuário API

Esta API se comunica com a **Usuário API** através de um **Feign Client**:  

- A **Usuário API** retorna informações do Usuário para **Agendador de tarefas API**  para compor o `UserDetails`. 
- O `UserDetails` é então usado pelo **Spring Security** para gerenciar permissões e autenticação  
- Link do repositório da **Usuário API** : https://github.com/WilkerSampaio/usuario-api


