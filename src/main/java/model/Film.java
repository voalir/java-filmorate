package model;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;
@Data
public class Film {
    @NotNull
    Integer id; //целочисленный идентификатор
    @Size(max = 200)
    String name;//название
    String description;//описание
    //TODO validate after date
    LocalDate releaseDate;//дата релиза
    @Positive
    Duration duration;//продолжительность фильма

}
