# Desafio Técnico PicPay

Repositório que contém uma solução para o desafio proposto no teste técnico para vaga de desenvolvedor Back-End do PicPay (possível encontrar no tal repositório: <a href='https://github.com/PicPay/picpay-desafio-backend'>https://github.com/PicPay/picpay-desafio-backend</a>)

## DESAFIO NÃO OFICIAL!
A resolução do problema proposto pelo PicPay não é oficial a esse repositório, sendo desenvolvido por mim apenas para "treinar" e consolidar conhecimentos de Java, sem querer aplicar a uma vaga real. 

## Especificações/implementações
O sistema consta com algumas implementações adicionais que não foram requisitadas no repositório do desafio.

- JWT para autenticação
- Hash de senha
- Docker para deploy
- Utilização do DDD (domain-driven design)

Além das implementações obrigatórias

- Criação de transações
- 2 tipos de usuário: lojista e usuário comum
- Validação para registro de usuário com o mesmo e-mail ou identificador (CPF ou CNPJ)
- Validação de saldo suficiente na conta do usuário antes de realizar transação
- Validação do usuário que está realizando a transação não ser do tipo "lojista"
- ETC

Além do mais, também possuem demais implementações a serem feitas conforme o tempo:

- Verificação de senha segura
- Transações serem registradas no banco de dados
- Implementação real de sistema para envio de e-mails

## Impossibilitações

Foram encontradas algumas dificuldades de implementar as requisições aos mocks, já que nem sempre eles estão disponíveis (no momento de desenvolvimento dessa aplicação, ambos estavam indisponíveis, sendo impossível implementar a requisição deles).
Contudo, já adianto que não é um problema implementa-los, e assim que estiverem disponíveis, irá ser lançado uma nova versão com a utilização dos mocks.

## Endpoints

### Autenticação

#### Registro
```
POST /api/auth/register
```
Endpoint para registrar-se na aplicação.
Exemplo de corpo da requisição:

```json
{
  "name": "Matheus Piccoli",
  "identifier": "123.456.789-00",
  "email": "matheus@gmail.com",
  "password": "Matheus123!",
  "type": "lojista"
}
```

#### Login
```
POST /api/auth/login
```
Endpoint para logar-se na aplicação.
Exemplo de corpo da requisição:

```json
{
  "email": "matheus@gmail.com",
  "password": "Matheus123!"
}
```

### Transações
```
POST /api/transaction
```

Endpoint para criar uma transação.
Exemplo de corpo da requisição:

```json
{
  "amount": 100.00,
  "payee": "arthur@gmail.com"
}
```

Exemplo de cabeçalho para autenticação:

```
Authorization: Bearer TOKEN_JWT_HERE
```

