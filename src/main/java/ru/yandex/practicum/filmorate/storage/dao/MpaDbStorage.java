package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getMpas() {
        String query = "select * from \"MPAs\"";
        return jdbcTemplate.query(query, getRowMapperMpas());
    }

    @Override
    public Mpa getMpaById(Integer id) {
        String query = "select * from \"MPAs\" where id = ?";
        List<Mpa> mpas = jdbcTemplate.query(query, getRowMapperMpas(), id);
        if (mpas.size() == 1) {
            return mpas.get(0);
        } else {
            throw new InvalidIdException("MPA with id=" + id + " not found");
        }
    }

    private RowMapper<Mpa> getRowMapperMpas() {
        return (rs, rowNum) -> {
            Mpa mpa = new Mpa();
            mpa.setId(rs.getInt("id"));
            mpa.setName(rs.getString("name"));
            return mpa;
        };
    }
}
