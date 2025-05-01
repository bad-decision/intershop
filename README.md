# Shop
Веб приложение "Витрина магазина":
- showcase-service - сервис витрины
- payment-service - сервис платежей
- keycloak - сервис аутентификации и авторизации

Используемые технологии:
- Spring Boot Framework
- Spring Data R2DBC
- Spring WebFlux
- OpenApi
- Redis
- Thymeleaf
- Lombok
- PostgreSQL
- Keycloak

Запуск приложения:
```
docker compose up
```
Также для локальной работы требуется отредактировать файл etc/hosts добавив строку
```
127.0.0.1 shop-keycloak
```

- После запуска приложение доступно по адресу: http://localhost:8080
- Rest сервис платежей доступен по адресу: http://localhost:8081
- Панель администрирования кейклок доступна по адресу: http://shop-keycloak:8091

Для тестирования добавлены пользователи login/pass:
- user01/user01
- user02/user02
- admin/admin - администратор Keycloak