# JavaCase
## Описание
Сервис для получения ФИО пользователя VK, а также признака участника группы VK.

## Запуск из под Windows
```
.\mvnw package
java -jar .\target\JavaCase-0.0.1-SNAPSHOT.jar
```
## API
- http://localhost:8080/api/reg - регистрация пользователя и получение JWT токена.

Response
```JSON
{
  "username":"romanblshkv",
  "password":"12345678qQ"
}
```

Request
```JSON
{
    "jwt-token": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJuY2FsbGllIiwiZXhwIjoxNjY3OTA2NjAwLCJpYXQiOjE2Njc4MjAyMDAsInVzZXJuYW1lIjoicm9tYW5ibHNoa3YifQ.F0qtfUKoAbMExYQoeZzFBuN3fW-cagCCpsnc2rHqFlY"
}
```
- http://localhost:8080/api/refreshToken - обновление устарвешего/утерянного JWT токена

Response
```JSON
{
  "username":"romanblshkv",
  "password":"12345678qQ"
}
```

Request
```JSON
{
    "jwt-token": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJuY2FsbGllIiwiZXhwIjoxNjY3OTA2NjAwLCJpYXQiOjE2Njc4MjAyMDAsInVzZXJuYW1lIjoicm9tYW5ibHNoa3YifQ.F0qtfUKoAbMExYQoeZzFBuN3fW-cagCCpsnc2rHqFlY"
}
```


- http://localhost:8080/api/vk/user - получение ФИО пользователя VK, а также признака участника группы VK.

Response

Headers: `vk_service_token - токен вк Authorization- JWT токен`

```JSON
{
    "user_id": "1",
    "group_id": "1"
}
```


Request
```JSON
{
    "last_name": "Durov",
    "first_name": "Pavel",
    "middle_name": "",
    "member": false
}
```

- http://localhost:8080/h2-console - достуцп к H2 in memory DB
- http://localhost:8080/swagger-ui/index.html - доступ к документации Swagger

## Что реализовано
- Rest-метод получения пользователя, статус признака участия в группе. Особенность, middle_name остутствует у пользователя ВК но в документации поле nickname описано как "Никнейм (отчество) пользователя."

- Валидация запросов и обработка ошибок
- Swagger
- Тесты
- Авторизация c помощью JWT токена
- каширование ответов от БД и Api VK (Spring Cache)
