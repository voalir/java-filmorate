CREATE TABLE IF NOT EXISTS "MPAs" (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name character varying
);

CREATE TABLE IF NOT EXISTS films
(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name character varying,
    description text,
    release_date date,
    duration integer,
    mpa integer REFERENCES "MPAs" (id)
);

CREATE TABLE IF NOT EXISTS users (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  email character varying,
  login character varying,
  name character varying,
  birthday date
);

CREATE TABLE IF NOT EXISTS genres (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name character varying
);

CREATE TABLE IF NOT EXISTS film_genre (
  film_id integer REFERENCES films (id),
  genre_id integer REFERENCES genres (id),
  PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS friends (
  user_id integer REFERENCES users (id),
  friend_id integer REFERENCES users (id),
  verify boolean,
  PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS likes (
  user_id integer REFERENCES users (id),
  film_id integer REFERENCES films (id)
);