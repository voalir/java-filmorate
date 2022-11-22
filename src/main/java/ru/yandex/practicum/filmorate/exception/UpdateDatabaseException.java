package ru.yandex.practicum.filmorate.exception;

public class UpdateDatabaseException extends RuntimeException {
    public UpdateDatabaseException(String message) {
        super(message);
    }
}
