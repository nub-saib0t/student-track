# Student Performance Tracker

Консольное приложение на Java для управления успеваемостью студентов.

## Структура проекта

```
src/
├── model/
│   ├── Student.java     — сущность «Студент»
│   ├── Course.java      — сущность «Курс»
│   └── Grade.java       — сущность «Оценка»
├── service/
│   └── TrackerService.java  — бизнес-логика и аналитика
└── main/
    └── Main.java        — консольный интерфейс (меню)
```

## Запуск

### 1. Компиляция

```bash
cd src
javac model/Student.java model/Course.java model/Grade.java
javac -cp . service/TrackerService.java
javac -cp . main/Main.java
```

### 2. Запуск

```bash
java -cp . main.Main
```

## Функционал

| Пункт меню | Описание |
|---|---|
| 1. Add student | Добавить студента (имя + группа, ID автоматический) |
| 2. Add course | Добавить курс (код + название) |
| 3. Assign grade | Выставить оценку студенту по курсу (0–100) |
| 4. Show student GPA | Показать средний балл студента |
| 5. Show top-3 students | Топ-3 студентов по успеваемости |
| 6. Course analytics | Средний балл по каждому курсу |
| 7. Students above GPA threshold | Студенты с GPA выше порога |
| 8. List all | Вывод всех студентов и курсов |
| 0. Exit | Выход |

## Технические детали

- Хранение данных: `ArrayList<Student>`, `ArrayList<Course>`, `ArrayList<Grade>`
- Быстрый поиск: `HashMap<Integer, Student>`, `HashMap<String, Course>`
- Валидация: оценка строго 0–100, числовой ввод защищён `try-catch (NumberFormatException)`
- Несуществующий ID/код не вызывает исключения, выводится сообщение об ошибке
