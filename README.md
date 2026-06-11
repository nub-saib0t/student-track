Система отслеживания успеваемости студентов

Консольное приложение на Java для учёта способности студентов. Управление студентами, курсами и приложениями без внешней базы данных — все данные хранятся в памяти с использованием стандартных коллекций Java.

Я

Приложение добавления студентов и курсов позволяет выставлять оценки, рассчитывать средний балл (GPA), наблюдать за топ-3 студентов по измерению и получать аналитику по курсам.

Структура проекта

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

интернет

JDK 8 или выше. Скачать можно на официальном сайте Oracle.

Посмотреть установку:

javac -version
java -version

Компиляция и запуск

Перейдите в каталог src и скомпилируйте все файлы:

cd src

javac model/Student.java model/Course.java model/Grade.java service/TrackerService.java main/Main.java

В Windows используйте обратный слэш:

javac model\Student.java model\Course.java model\Grade.java service\TrackerService.java main\Main.java

Запустить приложение:

java main.Main

райский

1. Add student
2. Add course
3. Assign grade
4. Show student GPA
5. Show top-3 students
6. Course analytics (average scores)
7. Students above GPA threshold
8. List all students / courses
0. Exit

Пример использования

Enter option: 1
Student name: Aisha Bekova
Group (e.g. CS-101): CS-22
Student added: Student{id=1, name='Aisha Bekova', group='CS-22'}

Enter option: 2
Course code (e.g. INF-101): INF-101
Course name: Introduction to Programming
Course added: Course{courseCode='INF-101', courseName='Introduction to Programming'}

Enter option: 3
Student ID: 1
Course code: INF-101
Score (0-100): 92
Grade assigned: Grade{student=Aisha Bekova, course=INF-101, score=92}

Enter option: 4
Student ID: 1
GPA of Aisha Bekova: 92.00

Архитектура

Проект разделен на три слоя. Модель пакета содержит доменные объекты — чистые классы с приватными полями, геттерами, сеттерами и методом toString. Пакет сервиса содержит всю бизнес-логику: добавление данных, расчёт GPA, аналитику. Основной пакет отвечает только за консольный интерфейс — чтение ввода и вывод результатов.

Для хранения использовались ArrayList и HashMap. Оценки принимаются строго в рамках 0–100. Числовой ввод защищён обработкой NumberFormatException. При поиске несуществующего студента или курсовой программы выводится сообщение об необходимости без аварийного завершения.

Автор

Фархатұлы Аят
