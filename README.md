# Commons Library

Uma biblioteca utilitária Kotlin projetada para simplificar tarefas comuns de desenvolvimento, como manipulação de CSV, padronização de respostas de API, gerenciamento de eventos e abstração de repositórios de banco de dados.

## Funcionalidades Principais

Esta biblioteca fornece um conjunto de ferramentas robustas e reutilizáveis:

*   **Manipulação de CSV**: Leitura assíncrona e eficiente de arquivos CSV com suporte a mapeamento personalizado e validação.
*   **Respostas Web**: Uma estrutura padronizada para respostas de API (sucesso, erro, criação, etc.).
*   **Sistema de Eventos**: Um despachante de eventos simples para desacoplar a lógica de negócios.
*   **Abstração de Banco de Dados**: Interfaces genéricas para repositórios, facilitando operações CRUD padrão.
*   **Value Objects**: Coleção de modelos de domínio comuns (Email, CPF/CNPJ, Dinheiro, Endereço, etc.).

## Classes Disponíveis

### IO / CSV
*   `io.csv.GenericCsvReader`: Leitor de CSV baseado em Kotlin Coroutines e Flow.
*   `io.csv.CsvTemplate`: Define a estrutura e regras de validação do CSV.

### Web
*   `web.Response<T>`: Wrapper genérico para respostas HTTP.
*   `web.pageable.Pagination`: Utilitário para paginação de dados.

### Eventos (Handles)
*   `handles.EventDispatcher`: Gerenciador central para despacho de eventos.
*   `handles.EventHandle<T>`: Interface para manipuladores de eventos.

### Banco de Dados (DB)
*   `db.repositories.BaseRepository<T, ID>`: Interface base para repositórios com operações CRUD e paginação.

### Modelos (Models)
*   `models.Email`, `models.Phone`, `models.Document` (CPF/CNPJ), `models.money.Money`, etc.

## Como Utilizar

### 1. Lendo um arquivo CSV

O `GenericCsvReader` permite processar arquivos CSV de forma reativa.

```kotlin
import io.csv.GenericCsvReader
import io.csv.CsvTemplate
import io.csv.CsvFieldMapping

// 1. Definir o template do CSV
val template = CsvTemplate(
    hasHeader = true,
    separator = ';',
    fields = listOf(
        CsvFieldMapping("nome", columnIndex = 0, required = true),
        CsvFieldMapping("email", columnIndex = 1, required = true)
    )
)

// 2. Instanciar o leitor
val csvReader = GenericCsvReader()

// 3. Processar o arquivo (exemplo dentro de uma coroutine)
val inputStream = File("usuarios.csv").inputStream()

csvReader.read(inputStream, template) { rowMap ->
    // Mapper: Converte o mapa de valores brutos para seu objeto de domínio
    Usuario(
        nome = rowMap["nome"] as String,
        email = rowMap["email"] as String
    )
}.collect { result ->
    if (result.success != null) {
        println("Usuário processado: ${result.success}")
    } else {
        println("Erro na linha ${result.lineNumber}: ${result.errors}")
    }
}
```

### 2. Padronizando Respostas de API

Utilize a classe `Response` para manter consistência nos retornos dos seus controllers.

```kotlin
import web.Response

// Sucesso (200 OK)
fun getUsuario(): Response<Usuario> {
    val usuario = usuarioService.find()
    return Response.success(data = usuario)
}

// Criação (201 Created)
fun criarUsuario(): Response<Usuario> {
    return Response.created(data = novoUsuario)
}

// Erro (500 ou personalizado)
fun erro(): Response<Nothing> {
    return Response.error(errors = listOf("Usuário não encontrado"), code = 404)
}
```

### 3. Despachando Eventos

Desacople ações utilizando o `EventDispatcher`.

```kotlin
import handles.EventDispatcher
import handles.EventHandle

// 1. Definir um evento
data class UsuarioCriadoEvent(val usuarioId: String)

// 2. Criar um manipulador
class EnviarEmailBoasVindas : EventHandle<UsuarioCriadoEvent> {
    override suspend fun handle(event: UsuarioCriadoEvent) {
        println("Enviando email para o usuário ${event.usuarioId}")
    }
}

// 3. Configurar e despachar
val handlers = mapOf(
    UsuarioCriadoEvent::class.java to listOf(EnviarEmailBoasVindas())
)
val dispatcher = EventDispatcher(handlers)

// Disparar o evento
dispatcher.dispatch(UsuarioCriadoEvent("123"))
```

## Requisitos de Compatibilidade

*   **Kotlin**: 1.5 ou superior.
*   **Kotlin Coroutines**: Necessário para operações assíncronas (CSV, Repositórios, Eventos).
*   **JDK**: 8 ou superior.

---
*Documentação gerada automaticamente baseada na estrutura do projeto.*
