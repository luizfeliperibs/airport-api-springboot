# ✈️ API REST – Sistema de Gerenciamento de Aeroportos

## 🧭 Descrição do Projeto
A **OpenFlights** é um projeto de código aberto dedicado a coletar e disponibilizar dados sobre aviação — incluindo **aeroportos, rotas de voo e companhias aéreas**.  
Neste trabalho prático, o objetivo é desenvolver uma **API REST completa** para gerenciar os cadastros de aeroportos ao redor do mundo, utilizando um **banco de dados relacional** e seguindo as boas práticas de design de APIs.

---

## 🎯 Objetivo
Construir uma API RESTful capaz de realizar operações de **CRUD** (Create, Read, Update, Delete) sobre os dados de aeroportos, conforme o dicionário de dados e endpoints definidos a seguir.

---

## 🧱 Dicionário de Dados

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id_aeroporto` | Inteiro | Chave primária que identifica de maneira única cada aeroporto. |
| `nome_aeroporto` | Texto | Nome do aeroporto. |
| `codigo_iata` | Texto | Código IATA (3 letras) que identifica o aeroporto. |
| `cidade` | Texto | Cidade onde o aeroporto está localizado. |
| `codigo_pais_iso` | Texto | Código do país no formato ISO 3166-1 alfa-2 (ex: `BR`, `US`). |
| `latitude` | Real | Latitude geográfica do aeroporto. |
| `longitude` | Real | Longitude geográfica do aeroporto. |
| `altitude` | Real | Altitude do aeroporto em metros. |

---

## 🗃️ Fonte de Dados
Os dados iniciais devem ser populados a partir do dataset **airports.csv**, disponível em:

🔗 [https://raw.githubusercontent.com/profdiegoaugusto/banco-dados/master/mysql/linguagem-consulta-dados/data/airports.csv](https://raw.githubusercontent.com/profdiegoaugusto/banco-dados/master/mysql/linguagem-consulta-dados/data/airports.csv)

---

## 🌐 Endpoints da API

| Método | URL | Descrição |
|---------|-----|-----------|
| `GET` | `/api/v1/aeroportos` | Retorna todos os aeroportos cadastrados. |
| `GET` | `/api/v1/aeroportos/{iata}` | Retorna um aeroporto específico pelo código IATA. |
| `POST` | `/api/v1/aeroportos` | Cadastra um novo aeroporto. |
| `PUT` | `/api/v1/aeroportos/{iata}` | Atualiza os dados de um aeroporto existente. |
| `DELETE` | `/api/v1/aeroportos/{iata}` | Remove um aeroporto do sistema. |

---

## 🧩 Exemplo de JSON

### ➕ POST /api/v1/aeroportos
```json
{
	"nomeAeroporto": "Aeroporto Internacional de Guarulhos",
	"codigoIata": "GRU",
	"cidade": "Guarulhos",
	"codigoPaisIso": "BR",
	"latitude": -23.44,
	"longitude": -46.47,
	"altitude": 750.00
}
```

### ✏️ PUT /api/v1/aeroportos/GIG
```json
{
	"nomeAeroporto": "Aeroporto Internacional do Galeão",
	"codigoIata": "GIG",
	"cidade": "Rio de Janeiro",
	"codigoPaisIso": "BR",
	"latitude": -22.809,
	"longitude": -43.250,
	"altitude": 28.00
}
```

---

## ⚙️ Requisitos Técnicos

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **Banco de dados:** H2 (para testes) ou MySQL/PostgreSQL
- **Ferramentas de build:** Maven
- **Ferramenta de testes:** JUnit 5

---

## 🧪 Testes Automatizados

O projeto deve conter **dois tipos de testes** — de **unidade** e de **integração** — usando os plugins adequados do Maven.

### 🔹 Testes de Unidade

#### Camada de Domínio
- `converterPesParaMetros(1000)` → deve retornar `304.8`
- `obterIsoPais("Brazil")` → deve retornar `"BR"`

#### Camada de Serviço
- Buscar um aeroporto por um IATA inexistente → deve lançar `AeroportoNaoEncontradoException`
- Tentar salvar um aeroporto com dados inválidos (ex: `codigoIata` com 4 letras, `altitude` negativa) → deve lançar exceção de validação.

---

### 🔸 Testes de Integração

| Cenário | Endpoint | Verificação |
|----------|-----------|-------------|
| Criar um novo aeroporto | `POST /api/v1/aeroportos` | Retorna **201 Created** e salva no banco. |
| Buscar aeroporto por IATA | `GET /api/v1/aeroportos/{iata}` | Retorna **200 OK** com os dados corretos. |
| Atualizar aeroporto existente | `PUT /api/v1/aeroportos/{iata}` | Retorna **200 OK** e atualiza no banco. |
| Excluir aeroporto | `DELETE /api/v1/aeroportos/{iata}` | Retorna **204 No Content**. |
| Buscar após exclusão | `GET /api/v1/aeroportos/{iata}` | Retorna **404 Not Found**. |

---

## 🧰 Configuração dos Plugins Maven

### Maven Surefire Plugin
Executa os testes de **unidade** (`*Test.java`).

```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-surefire-plugin</artifactId>
	<version>3.2.3</version>
</plugin>
```

### Maven Failsafe Plugin
Executa os testes de **integração** (`*IT.java`).

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

## 🚀 Como Executar

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/seuusuario/api-aeroportos.git
cd api-aeroportos
```

### 2️⃣ Rodar o projeto
```bash
mvn spring-boot:run
```

### 3️⃣ Acessar os endpoints
- **Base URL:** `http://localhost:8080/api/v1/aeroportos`

---

## 🧠 Dicas e Boas Práticas

- Utilize **DTOs** para isolar as entidades JPA do retorno da API.
- Adicione `@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})` nas entidades para evitar erros de serialização.
- Implemente tratamento global de exceções (`@ControllerAdvice`) para erros como 404 e validações.
- Configure logs adequados para depuração (`application.properties`).

---

## 📚 Licença
Projeto desenvolvido para fins acadêmicos com base em dados do projeto [OpenFlights](https://openflights.org/).

---

✈️ Desenvolvido com 💻 por **Luiz Felipe Ribeiro Souza**
