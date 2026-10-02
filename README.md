# expense-tracker

Консольное приложение для учёта личных расходов. Данные хранятся в файле `expenses.csv`.

## Сборка и запуск

    mvn -q package
    java -cp target/classes ru.expenses.Main add 2026-09-01 350.50 food
    java -cp target/classes ru.expenses.Main list

Команды: add, list, total.
Данные хранятся в файле expenses.csv в текущей папке.
Требуется Java 17 или новее.
Документация лежит в папке docs.
Автор: person1.
Планы развития: docs/roadmap.md.
Глоссарий: docs/glossary.md.
