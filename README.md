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
```

#### Переменные окружения в .env

Описание:
1. POSTGRES_DB - имя базы данных
2. POSTGRES_HOST - хост базы данных (в данном случае имя сервиса в docker-compose)
3. POSTGRES_PASSWORD - пароль от базы данных
4. POSTGRES_PORT - порт, требующийся для работы базы данных
5. POSTGRES_USER - имя пользователя базы данных
6. SECRET_KEY - секретный ключ от вашего сайта

Далее введите команду

```shell
docker-compose up --build
```
Готово! Сервер запущен.

Чтобы остановить работу контейнеров, в терминале, откуда вы запускали docker-compose нажмите Ctrl+C (Control + C для Mac)
</details>
