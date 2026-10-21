# expense-tracker

Консольное приложение для учёта личных расходов. Данные хранятся в файле `expenses.csv`.

## Сборка и запуск

    mvn -q package
    java -jar target/expense-tracker-1.0.0.jar add 2026-09-01 350.50 food
    java -jar target/expense-tracker-1.0.0.jar list

Готовый jar можно скачать со страницы Releases.

Команды: add, list, total, month <yyyy-MM> (сумма за месяц). Сумму можно вводить через точку или запятую.
Данные хранятся в файле expenses.csv в текущей папке.
Требуется Java 17 или новее.
Документация лежит в папке docs.
Автор: команда проекта.
Планы развития: docs/roadmap.md.
Глоссарий: docs/glossary.md.
