# java-filmorate
Template repository for Filmorate project.

![ER -диаграмма таблиц хранилища](/filmorate-DB%20diagram.png)

Таблицы:
films - фильмы,
users - пользователи,
mpa_ratings - рейтинг Ассоциации кинокомпаний,
likes - лайки фильма,
genres - справочник жанров,
film_genre - жанры фильма,
friends - друзья

Пример запроса
Все пользователи:
select * from users

Заявки в друзья пользователя:
select friend_id from friends where user_id = ?