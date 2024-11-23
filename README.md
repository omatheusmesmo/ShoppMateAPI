Lista de Compras API
====================

Descrição
---------

A Lista de Compras API é uma aplicação para gerenciar itens de uma lista de compras. A API permite adicionar, editar, remover e buscar itens na lista, oferecendo uma interface RESTful para interações com os dados.

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

-   Implementar autenticação e autorização.

-   Adicionar paginação e filtros nos endpoints de busca.

-   Melhorar a documentação com Swagger/OpenAPI.

Contribuição
------------

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

Licença
-------

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.
