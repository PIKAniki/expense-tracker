# Разработка

## Сборка и тесты

    mvn verify

Команда компилирует проект, запускает unit-тесты (`*Test`, плагин surefire), интеграционные тесты (`*IT`, плагин failsafe) и строит отчёт о покрытии JaCoCo в `target/site/jacoco`. Документация API: `mvn javadoc:javadoc`, результат в `target/reports/apidocs`.

## Ветки

- `main` – стабильная ветка. Прямой push запрещён правилом репозитория: изменения попадают только через pull request, и только если проверка `build` прошла.
- `development` – разработка новых функций и исправлений.
- `release/<версия>` – подготовка выпуска: версия в `pom.xml`, `CHANGELOG.md`, мелкие исправления.

## CI/CD

CI/CD построен на GitHub Actions и состоит из трёх workflow в `.github/workflows`:

| Файл | Когда запускается | Что делает |
|------|-------------------|------------|
| `build.yml` | push в `main`, `development`, `release/**`; pull request в `main` и `development`; вручную | `mvn -B verify`: сборка, unit- и интеграционные тесты, отчёт JaCoCo; отчёты сохраняются как артефакт `test-reports` |
| `docs.yml` | после успешного `Build` на ветке `main`; вручную | генерирует Javadoc и публикует его на [GitHub Pages](https://pikaniki.github.io/expense-tracker/) |
| `release.yml` | push тега `v*` | ещё раз прогоняет тесты, собирает jar и создаёт GitHub Release |

Почему push проверяется не во всех ветках. Долгоживущие ветки (`main`, `development`, `release/**`) проверяются на каждый push – это их фактическое состояние. Изменения в остальных ветках попадают в долгоживущие только через pull request, а pull request проверяется отдельно (на результат слияния), поэтому push в такие ветки CI не запускает – иначе каждый коммит проверялся бы дважды.

Pull request в `main` сливается автоматически (auto-merge), когда проходит проверка `build`. Прямой push в `main` запрещён правилом репозитория.

## Выпуск версии

1. Создать ветку `release/X.Y.Z` от `main`, выставить версию `X.Y.Z` в `pom.xml`, дополнить `CHANGELOG.md`.
2. Открыть pull request в `main` и включить auto-merge: `gh pr merge --auto --merge`.
3. После слияния поставить тег на `main` и отправить его:

        git tag -a vX.Y.Z -m "Release X.Y.Z"
        git push origin vX.Y.Z

4. Workflow `release.yml` создаст релиз с jar. Затем поднять версию в `main` до следующей `-SNAPSHOT`.
