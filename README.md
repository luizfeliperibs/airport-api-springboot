# ✈️ API REST – Sistema de Gerenciamento de Aeroportos

## 📌 Objetivo do Projeto
Desenvolver uma **API REST completa** para gerenciar aeroportos do mundo inteiro, permitindo:
- Cadastro
- Consulta
- Atualização
- Exclusão

O projeto utiliza dados do OpenFlights e deve seguir boas práticas de arquitetura, testes automatizados e modelagem de API.

---

## 🧰 Tecnologias Utilizadas
- **Java 17+**
- **Spring Boot 3+**
- **Spring Web**
- **Spring Data JPA**
- **Maven**
- **MySQL** ou **H2 Database**
- **JUnit 5**
- **Mockito** (opcional)
- **Maven Surefire Plugin** (Testes de Unidade)
- **Maven Failsafe Plugin** (Testes de Integração)

---

## ⚙️ Configuração do Ambiente

### 1️⃣ Clone o repositório
```bash
git clone https://github.com/seuusuario/api-aeroportos.git
cd api-aeroportos
```

### 2️⃣ Instale as dependências
```bash
mvn clean install
```

### 3️⃣ Configure o banco no `application.properties`

#### Exemplo usando **MySQL**
```properties
spring.datasource.url=jdbc:mysql://localhost/airport_api
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.flyway.baseline-on-migrate=true

```

#### Exemplo usando **H2** (para testes)
```properties
spring.datasource.url=jdbc:h2:mem:aeroportos
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ Como Executar a Aplicação
```bash
mvn spring-boot:run
```

A API ficará disponível em:
```
http://localhost:8080/api/v1/aeroportos
```

---

## 🌐 Endpoints da API

| Método | URL | Descrição |
|--------|-----|-----------|
| `GET` | `/api/v1/aeroportos` | Lista todos os aeroportos |
| `GET` | `/api/v1/aeroportos/{iata}` | Busca aeroporto pelo código IATA |
| `POST` | `/api/v1/aeroportos` | Cadastra um novo aeroporto |
| `PUT` | `/api/v1/aeroportos/{iata}` | Atualiza um aeroporto existente |
| `DELETE` | `/api/v1/aeroportos/{iata}` | Remove um aeroporto |

---

## 🧪 Como Executar os Testes

### 🔹 Testes de Unidade
Arquivos terminam com:
```
*Test.java
```

Executar:
```bash
mvn test
```

### 🔸 Testes de Integração
Arquivos terminam com:
```
*IT.java
```

Executar:
```bash
mvn verify
```

Esse comando roda **testes de unidade + integração** automaticamente.

---

## 🧰 Configuração dos Plugins Maven

### Maven Surefire Plugin – Testes de Unidade
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.3</version>
</plugin>
```

### Maven Failsafe Plugin – Testes de Integração
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>3.2.3</version>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
                <goal>verify</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

---

## 📚 Fonte dos Dados
Dataset inicial (airports.csv):  
https://raw.githubusercontent.com/profdiegoaugusto/banco-dados/master/mysql/linguagem-consulta-dados/data/airports.csv

---

## ✨ Autor
**Luiz Felipe Ribeiro Souza**  
Projeto acadêmico baseado no conjunto OpenFlights.
