To top button component.

Этот компонент представляет собой кнопку для прокрутки контента вверх.

Кнопка появляется в нижней правой части scrollable элемента.

При клике на кнопку, компонент запоминает положение экрана, и при повторном клике возвращается в запомненное положение (при условии, если пользователь не промотал в ручную дальше сохраненной позиции)

## Применение

Подключить репозиторий и зависимость в build.gradle:

```groovy
...
repositories {
    maven {
        url 'https://maven.pkg.github.com/lood/jmix-to-top-button'
    }
}
...
dependencies {
  ...
  
  ...
}
...
```

