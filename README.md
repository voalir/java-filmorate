# java-filmorate
Template repository for Filmorate project.

![ER -диаграмма таблиц хранилища](/filmorate-DB%20diagram.png)

Таблицы:
film - фильмы
user - пользователи
MPA - рейтинг Ассоциации кинокомпаний
like - лайки фильма
genre - справочник жанров
film_genre - жанры фильма
friend - друзья

Пример запроса
Все пользователи:
select * from user

Заявки в друзья пользователя:
select friend_id from friend where user_id = ?