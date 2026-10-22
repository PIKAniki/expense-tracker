# expense-tracker

[![Build](https://github.com/PIKAniki/expense-tracker/actions/workflows/build.yml/badge.svg)](https://github.com/PIKAniki/expense-tracker/actions/workflows/build.yml)

Консольное приложение для учёта личных расходов. Данные хранятся в файле `expenses.csv`.

## Сборка и запуск

    mvn -q package
    java -jar target/expense-tracker-*.jar add 2026-09-01 350.50 food
    java -jar target/expense-tracker-*.jar list

Готовый jar можно скачать со страницы Releases.

Команды: `add`, `list`, `total`, `month <yyyy-MM>` (сумма за месяц). Сумму можно вводить через точку или запятую.
Данные хранятся в файле expenses.csv в текущей папке.
Требуется Java 17 или новее.
Документация: [руководство пользователя](docs/user-guide.md), [разработка и CI](docs/development.md), [Javadoc](https://pikaniki.github.io/expense-tracker/).
Автор: команда проекта.
Планы развития: docs/roadmap.md.
Глоссарий: docs/glossary.md.
