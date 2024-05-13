Данный проект является серверной частью веб - приложения, которое представляет собой сайт для публикации записей. Сделан с использованием Spring Boot. Клиентская часть приложения: https://github.com/maksim25y/clientApp

</details>
<details><summary>Запуск</summary>
Для того, чтобы запустить необходимо проделать следующие шаги на Windows, установите [Git Bash](https://git-scm.com/)

1. Склонируйте репозиторий

```shell
git clone git@github.com:maksim25y/InstagramApp.git
```

2. Скачайте и установите Docker

Скачать и найти инструкцию по установке вы можете на официальном сайте [Docker](https://www.docker.com)

3. Запустите сервер в Docker

Для этого откройте терминал и перейдите в папку репозитория

```shell
cd InstagramApp
cd demo
```

#### Переменные окружения в .env

Описание:
1. POSTGRES_USER - логин для БД
2. POSTGRES_PASSWORD - пароль от базы данных
3. SPRING_DATASOURCE_URL - адрес БД
4. SPRING_DATASOURCE_USERNAME - логин для БД, но для Spring
5. SPRING_DATASOURCE_PASSWORD - пароль для БД, но для Spring

Далее введите команду

```shell
docker-compose up --build
```
Готово! Сервер запущен.
Далее переходите к запуску клиента, если ещё не запустили: https://github.com/maksim25y/clientApp

Чтобы остановить работу контейнеров, в терминале, откуда вы запускали docker-compose нажмите Ctrl+C (Control + C для Mac)
</details>
