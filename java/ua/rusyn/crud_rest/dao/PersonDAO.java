package ua.rusyn.crud_rest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.rusyn.crud_rest.models.Person;
import ua.rusyn.crud_rest.util.PersonMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person;", new BeanPropertyRowMapper<>(Person.class));
    }
    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email =?", new Object[]{email}, new PersonMapper())
                .stream().findAny();
    }
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id =?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO person(name,age,email) VALUES(?,?,?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update(
                "UPDATE person SET name= ? , age=? ,email = ? WHERE id = ?",
                updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

}
