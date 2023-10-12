# Transaction

## Техническое задание: https://github.com/avito-tech/autumn-2021-intern-assignment

## URI: http://localhost:8080

### Описание запросов к RestController


#### GET запросы:

* **/accounts**
    + Описание:
      + Возвращает информацию об аккаунте(ах)
    + Параметры:
      + name *- возвращает информацию об аккаунте с таким "name"*
      + id *- возвращает информацию об аккаунте с таким "id"*
      + без параметров *- возвращает список всех аккаунтов* 
    + Примеры запросов:
      + (http://localhost:8080/accounts?id=1)
      + (http://localhost:8080/accounts?name=Nick)
      + (http://localhost:8080/accounts)


* **/money/conversion**
  + Описание:
    + Конвертирует баланс пользователя в выбранную валюту
  + Параметры:
    + id *(Обязателен)*
    + valute *(Обязателен)*
  + Примеры запросов:
    + (http://localhost:8080/money/conversion?id=1&valute=USD)


* **/log**
  + Описание:
    + Возвращает историю операций для выбранного аккаунта
  + Тело запроса *(JSON)*:
    + pageNum *(defaultValue = "0") - возвращает необходимую страницу операций*
    + pageSize *(defaultValue = "3") - определяет количество записей на странице*
    + sortBy *(defaultValue = "id") - определяет поле для сортировки*
    + orderDirection *(defaultValue = "desc") - определяет порядок сортировки*
    + accountid *(Обязателен)*
  + Примеры запросов:
    + (http://localhost:8080/log)


### POST запросы:

* **"/account/creation"**
    + Описание:
      + Создание аккаунта
    + Тело запроса *(JSON)*:
      + name
    + Примеры запросов:
      + (http://localhost:8080/account/creation)


* **/money/addition**
  + Описание:
    + Добавление средств на аккаунт
  + Тело запроса *(JSON)*:
    + id
    + amount
  + Примеры запросов:
    + (http://localhost:8080/money/addition)


* **/money/subtract**
  + Описание:
    + Уменьшение средств на аккаунте
  + Тело запроса *(JSON)*:
    + id
    + amount
  + Примеры запросов:
    + (http://localhost:8080/money/subtract)


* **/money/transfer**
  + Описание:
    + Перевод средств с одного аккаунта на другой
  + Тело запроса *(JSON)*:
    + SenderAccountId 
    + ReceiverAccountId
    + amount
  + Примеры запросов:
    + (http://localhost:8080/money/transfer)


### DELETE запросы:

* **/account/deletion**
  + Описание:
    + Удаление аккаунта
  + Тело запроса *(JSON)*:
    + name
  + Примеры запросов:
    + (http://localhost:8080/account/deletion)
    