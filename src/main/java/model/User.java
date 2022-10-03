package model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    @NotNull
    Integer id;//целочисленный идентификатор
    @Email
    String email;//электронная почта
    @NotBlank
    String login;//логин пользователя
    String name;//имя для отображения
    @Past
    LocalDate birthday;// дата рождения
}
