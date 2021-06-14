# Hire.me
Um pequeno projeto para testar suas habilidades como programador.


## Solu��o

### Stack
Java 11  
Spring Boot  
Redis  
Docker  

### Bibliotecas utilizadas
Guava - Utilizada para gerar o hash com o algoritmo MurmurHash3  
Commons Validator - Utilizada para validar as URLs  

### Instru��es para execu��o

Foram criados 2 projetos, um para a api e o outro para simular um cliente. Cada projeto tem seu Dockerfile para gera��o da sua imagem.  
Para executar as aplica��es s�o necess�rios os seguintes passos:
1. Gerar o pacote de cada projeto, via maven  
mvn clean package
2. Gerar as imagens e subir atrav�s do Docker  
docker-compose up --build 

## Instru��es Gerais

1. *Clone* este reposit�rio
2. Em seu *fork*, atenda os casos de usos especificados e se desejar tamb�m os bonus points
3. Envio um e-mail para rh@bemobi.com.br com a seu Nome e endere�o do repositorio.

## Projeto

O projeto consiste em reproduzir um encurtador de URL's (apenas sua API), simples e com poucas fun��es, por�m com espa�o suficiente para mostrar toda a gama de desenho de solu��es, escolha de componentes, mapeamento ORM, uso de bibliotecas de terceiros, uso de GIT e criatividade.

O projeto consiste de dois casos de uso: 

1. Shorten URL
2. Retrieve URL

### 1 - Shorten URL
![Short URL](http://i.imgur.com/MFB7VP4.jpg)

1. Usuario chama a API passando a URL que deseja encurtar e um parametro opcional **CUSTOM_ALIAS**
    1. Caso o **CUSTOM_ALIAS** j� exista, um erro especifico ```{ERR_CODE: 001, Description:CUSTOM ALIAS ALREADY EXISTS}``` deve ser retornado.
    2. Toda URL criada sem um **CUSTOM_ALIAS** deve ser reduzida a um novo alias, **voc� deve sugerir um algoritmo para isto e o porqu�.**
    
2. O Registro � colocado em um reposit�rio (*Data Store*)
3. � retornado para o cliente um resultado que contenha a URL encurtada e outros detalhes como
    1. Quanto tempo a opera��o levou
    2. URL Original

Exemplos (Voc� n�o precisa seguir este formato):

* Chamada sem CUSTOM_ALIAS
```
PUT http://shortener/create?url=http://www.bemobi.com.br

{
   "alias": "XYhakR",
   "url": "http://shortener/u/XYhakR",
   "statistics": {
       "time_taken": "10ms",
   }
}
```

* Chamada com CUSTOM_ALIAS
```
PUT http://shortener/create?url=http://www.bemobi.com.br&CUSTOM_ALIAS=bemobi

{
   "alias": "bemobi",
   "url": "http://shortener/u/bemobi",
   "statistics": {
       "time_taken": "12ms",
   }
}
```

* Chamada com CUSTOM_ALIAS que j� existe
```
PUT http://shortener/create?url=http://www.github.com&CUSTOM_ALIAS=bemobi

{
   "alias": "bemobi",
   "err_code": "001",
   "description": "CUSTOM ALIAS ALREADY EXISTS"
}
```

### 2 - Retrieve URL
![Retrieve URL](http://i.imgur.com/f9HESb7.jpg)

1. Usuario chama a API passando a URL que deseja acessar
    1. Caso a **URL** n�o exista, um erro especifico ```{ERR_CODE: 002, Description:SHORTENED URL NOT FOUND}``` deve ser retornado.
2. O Registro � lido de um reposit�rio (*Data Store*)
3. Esta tupla ou registro � mapeado para uma entidade de seu projeto
3. � retornado para o cliente um resultado que contenha a URL final, a qual ele deve ser redirecionado automaticamente

## Stack Tecnol�gico

N�o h� requerimentos espec�ficos para linguagens, somos poliglotas. Utilize a linguagem que voc� se sente mais confort�vel.

## Bonus Points

1. Crie *testcases* para todas as funcionalidades criadas
2. Crie um *endpoint* que mostre as dez *URL's* mais acessadas 
3. Crie um *client* para chamar sua API
4. Fa�a um diagrama de sequencia da implementa��o feita nos casos de uso (Dica, use o https://www.websequencediagrams.com/)
5. Monte um deploy da sua solu��o utilizando containers 
