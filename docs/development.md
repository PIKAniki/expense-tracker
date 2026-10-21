# Разработка

## Сборка и тесты

    mvn verify

Команда компилирует проект, запускает unit-тесты (`*Test`, плагин surefire), интеграционные тесты (`*IT`, плагин failsafe) и строит отчёт о покрытии JaCoCo в `target/site/jacoco`. Документация API: `mvn javadoc:javadoc`, результат в `target/reports/apidocs`.

## Ветки

- `main` – стабильная ветка. Прямой push запрещён правилом репозитория: изменения попадают только через pull request, и только если проверка `build` прошла.
- `development` – разработка новых функций и исправлений.
- `release/<версия>` – подготовка выпуска: версия в `pom.xml`, `CHANGELOG.md`, мелкие исправления.

## CI/CD

Workflow `.github/workflows/main.yml` (GitHub Actions) состоит из трёх задач:

- `build` – запускается на каждый push в любую ветку и на каждый pull request в `main`: `mvn -B verify`, отчёты тестов сохраняются как артефакт `test-reports`;
- `docs` – после push в `main` генерирует Javadoc и публикует его на [GitHub Pages](https://pikaniki.github.io/expense-tracker/);
- `release` – после push тега `v*` собирает jar и создаёт GitHub Release.

Pull request в `main` сливается автоматически (auto-merge), когда проходит `build`.

## Выпуск версии

1. Создать ветку `release/X.Y.Z` от `main`, выставить версию `X.Y.Z` в `pom.xml`, дополнить `CHANGELOG.md`.
2. Открыть pull request в `main` и включить auto-merge: `gh pr merge --auto --merge`.
3. После слияния поставить тег на `main` и отправить его:

        git tag -a vX.Y.Z -m "Release X.Y.Z"
        git push origin vX.Y.Z

4. Задача `release` создаст релиз с jar. Затем поднять версию в `main` до следующей `-SNAPSHOT`.
