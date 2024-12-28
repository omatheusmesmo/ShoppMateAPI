Lista de Compras API
====================

Descrição
---------

A Lista de Compras API é uma solução moderna e eficiente para gerenciar itens de uma lista de compras, projetada para facilitar a vida dos usuários. A aplicação oferece uma interface RESTful robusta, permitindo a manipulação completa dos itens na lista, incluindo adição, edição, remoção e busca. Além disso, a API possui funcionalidades avançadas de autenticação e autorização de usuários, garantindo segurança e privacidade dos dados.

Destaques do Projeto
-----------------

- **Gestão Completa de Itens:** Adicione, edite, remova e busque itens de maneira simples e eficiente.

- **Autenticação e Autorização:** Utiliza JWT (JSON Web Token) para autenticação segura e controle de acesso.

- **Documentação Interativa:** Integrada com SpringDoc e Swagger para documentação clara e interativa da API.

- **Versionamento de Banco de Dados:** Flyway para migrações de banco de dados, garantindo consistência e facilidade de atualização.

Tecnologias Utilizadas
---------

**Frameworks:** *Spring Boot, Spring Data JPA, Spring Security, SpringDoc*

**Testes:** *JUnit, Mockito, Spring Boot Test*

**Banco de dados:** *H2, PostgreSQL*

**Ferramentas de Construção:** *Maven*

**Utilitários:** *Lombok, Flyway*

**Autenticação e Autorização:** *JWT (JSON Web Token)*

**Documentação:** *SpringDoc, Swagger*

**Cobertura de Testes:** *JaCoCo*

Endpoints
---------

### GET /item

Retorna todos os itens da lista de compras.

-   **Response:** `200 OK`

```
[
    {
        "id": 1,
        "nome": "Arroz",
        "quantidade": 2,
        "categoria": "Alimentos",
        "comprado": false
    },
    ...
]

```

### POST /item

Adiciona um novo item à lista de compras.

-   **Request Body:**

```
{
    "nome": "Feijão",
    "quantidade": 1,
    "categoria": "Alimentos",
    "comprado": false
}

```

-   **Response:** `201 Created`

```
{
    "id": 2,
    "nome": "Feijão",
    "quantidade": 1,
    "categoria": "Alimentos",
    "comprado": false
}

```

### PUT /item

Atualiza um item existente na lista de compras.

-   **Request Body:**

```
{
    "id": 1,
    "nome": "Arroz Integral",
    "quantidade": 3,
    "categoria": "Alimentos",
    "comprado": false
}

```

-   **Response:** `200 OK`

```
{
    "id": 1,
    "nome": "Arroz Integral",
    "quantidade": 3,
    "categoria": "Alimentos",
    "comprado": false
}

```

### DELETE /item/{id}

Remove um item da lista de compras pelo ID.

-   **Response:** `204 No Content`

Configuração
------------

Para configurar e executar a aplicação localmente:

1.  Clone o repositório:

```
git clone https://github.com/omatheusmesmo/ListaDeComprasAPI.git

```

1.  Navegue até o diretório do projeto:

```
cd ListaDeComprasAPI

```

1.  Execute a aplicação:

```
./mvnw spring-boot:run

```

Estrutura do Projeto
--------------------

-   **Controller:** Gerencia as rotas e interações com a API.

-   **Service:** Contém a lógica de negócios.

-   **Repository:** Interage com o banco de dados.

Melhorias Futuras
-----------------

-   Implementação de Cache para aumentar a performance.

-   Adicionar paginação e filtros nos endpoints de busca.

-   Adicionar Logging centralizado.
  
-   Configurar pipelines de CI/CD para automatizar builds, testes e deploys.

Contribuição
------------

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

Licença
-------

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.
 