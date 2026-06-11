# Student Performance Tracker

Консольное приложение на Java для учёта успеваемости студентов. Управление студентами, курсами и оценками без внешних баз данных — все данные хранятся в памяти с использованием стандартных коллекций Java.

## Описание

Приложение позволяет добавлять студентов и курсы, выставлять оценки, рассчитывать средний балл (GPA), смотреть топ-3 студентов по успеваемости и получать аналитику по курсам.

## Структура проекта

```
student-tracker/
└── src/
    ├── model/
    │   ├── Student.java         сущность «Студент» (id, name, group)
    │   ├── Course.java          сущность «Курс» (courseCode, courseName)
    │   └── Grade.java           сущность «Оценка» (student, course, score)
    ├── service/
    │   └── TrackerService.java  бизнес-логика и аналитика
    └── main/
        └── Main.java            консольное меню, точка входа
```

## Требования

JDK 8 или выше. Скачать можно на официальном сайте Oracle.

Проверить установку:

```
javac -version
java -version
```

## Компиляция и запуск

Перейти в папку src и скомпилировать все файлы:

```
cd src

javac model/Student.java model/Course.java model/Grade.java service/TrackerService.java main/Main.java
```

На Windows использовать обратный слэш:

```
javac model\Student.java model\Course.java model\Grade.java service\TrackerService.java main\Main.java
```

Запустить приложение:

```
java main.Main
```

## Меню приложения

```
1. Add student
2. Add course
3. Assign grade
4. Show student GPA
5. Show top-3 students
6. Course analytics (average scores)
7. Students above GPA threshold
8. List all students / courses
0. Exit
```

## Пример использования

```
Enter option: 1
Student name: Ayat
Group (e.g. CS-101): CS-22
Student added: Student{id=1, name='Ayat', group='CS-22'}

Enter option: 2
Course code (e.g. INF-101): INF-101
Course name: Introduction to Programming
Course added: Course{courseCode='INF-101', courseName='Introduction to Programming'}

Enter option: 3
Student ID: 1
Course code: INF-101
Score (0-100): 92
Grade assigned: Grade{student=Ayat, course=INF-101, score=92}

Enter option: 4
Student ID: 1
GPA of Ayat: 92.00
```

## Архитектура

Проект разделён на три слоя. Пакет model содержит доменные объекты — чистые классы с приватными полями, геттерами, сеттерами и методом toString. Пакет service содержит всю бизнес-логику: добавление данных, расчёт GPA, аналитику. Пакет main отвечает только за консольный интерфейс — чтение ввода и вывод результатов.

Для хранения используются ArrayList и HashMap. Оценки принимаются строго в диапазоне 0–100. Числовой ввод защищён обработкой NumberFormatException. При обращении к несуществующему студенту или курсу программа выводит сообщение об ошибке без аварийного завершения.

## Автор
Фархатұлы Аят
