package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendsPair {
    Integer FriendId;
    Boolean verify;
    private Integer userId;
}
