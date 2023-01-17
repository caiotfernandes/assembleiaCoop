
# assembleiaCoop
Projeto de avaliação técnica para a DBC Company de um sistema de votação de uma assembléia cooperativa.



## Documentação da API

### AssociadosController

#### Inserir Associado

```http
  POST /associados
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `name` | `string` | **Obrigatório**. Nome para o associado. |

#### Retorna um associado

```http
  GET /associados/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `long` | **Obrigatório**. O ID do Associado |

### PautaController

#### Inserir Pauta

```http
  POST /pautas
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `name` | `string` | **Obrigatório**. Nome para a Pauta. |

#### Retorna uma Pauta

```http
  GET /pautas/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `long` | **Obrigatório**. O ID da Pauta |


### SessaoController

#### Abrir uma Sessao

```http
  POST /sessao
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `pautaId` | `long` | **Obrigatório**. Id da Pauta. |
| `endDate` | `date` | *Opcional*. Data de expiração da sessão(dd/MM/yyyy HH:mm:ss). |

#### Consultar uma Sessao

```http
  GET /sessao/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `long` | **Obrigatório**. O ID da Sessão. |

#### Votar em uma Sessao

```http
  GET /votar
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `sessaoId`      | `long` | **Obrigatório**. O ID da Sessão. |
| `associadoId`      | `long` | **Obrigatório**. O ID do Associado. |
| `voto`      | `string` | **Obrigatório**. Voto('SIM'/'NAO') |

#### Consultar resultado de uma Sessao

```http
  GET /resultados/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `long` | **Obrigatório**. O ID da Sessão. |



## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn test
```

Os testes foram separados em testes unitários e testes de integração, sendo os testes da camada de serviço os testes unitários e os testes da camada controller, testes de integração.
Para os testes de integração o banco H2 roda em memória.


## Autor

- [@caiotfernandes](https://www.github.com/caiotfernandes)

