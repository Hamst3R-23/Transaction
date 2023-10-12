# Transaction

## URI: http://localhost:8080

### Описание запросов к RestController


#### GET запросы:

* **/account**
  + Параметры:
    + id 
    + без параметров, возвращает список всех аккаунтов 
  + Примеры запросов:
    + (http://localhost:8080/account?id=1)
    + (http://localhost:8080/account)


* **/accounts**
    + Параметры:
        + name
        + без параметров, возвращает список всех аккаунтов 
    + Примеры запросов:
        + (http://localhost:8080/accounts?name=Max)
        + (http://localhost:8080/accounts)


* **/convert**
  + Параметры:
    + id *(Обязателен)*
    + valute *(Обязателен)*
  + Примеры запросов:
    + (http://localhost:8080/convert?id=1&valute=USD)


* **/log**
  + Параметры:
    + pageNum *(defaultValue = "0")*
    + pageSize *(defaultValue = "3")*
    + sortBy *(defaultValue = "id")*
    + orderQue *(defaultValue = "desc")*
    + accountid *(Обязателен)*
  + Примеры запросов:
    + (http://localhost:8080/log?pageSize=5&sortBy=amount&orderQue=asc&accountid=1&pageNum=0)
    + (http://localhost:8080/log?accountid=1)


### POST запросы:

* **/set**
    + Тело запроса:
      + name *(JSON)*
    + Примеры запросов:
      + (http://localhost:8080/set)


* **/delete**
  + Тело запроса:
    + name *(JSON)*
  + Примеры запросов:
    + (http://localhost:8080/delete)


* **/add**
  + Тело запроса:
    + id *(JSON)*
    + amount *(JSON)*
  + Примеры запросов:
    + (http://localhost:8080/add)


* **/subtract**
  + Тело запроса:
    + id *(JSON)*
    + amount *(JSON)*
  + Примеры запросов:
    + (http://localhost:8080/subtract)


* **/transfer**
  + Тело запроса:
    + SenderAccountId *(JSON)*
    + ReceiverAccountId *(JSON)*
    + amount *(JSON)*
  + Примеры запросов:
    + (http://localhost:8080/transfer)
    