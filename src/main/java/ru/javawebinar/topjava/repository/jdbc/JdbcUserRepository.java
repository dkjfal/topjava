package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.validate;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) != 0) {
            updateRoles(user);
        } else {
            return null;
        }

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        try {
            List<User> users = jdbcTemplate.queryForObject("SELECT * FROM users JOIN user_roles ur on users.id = ur.user_id WHERE id=?", new UserMapper(), id);
            return DataAccessUtils.singleResult(users);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Override
    public User getByEmail(String email) {
        try {
            List<User> users = jdbcTemplate.queryForObject("SELECT * FROM users JOIN user_roles ur on users.id = ur.user_id WHERE email=?", new UserMapper(), email);
            return DataAccessUtils.singleResult(users);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users JOIN user_roles ur on users.id = ur.user_id ORDER BY name, email", new UserMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Transactional
    public void updateRoles(User user) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        insertRoles(user);
    }

    @Transactional
    public void insertRoles(User user) {
        List<Role> roles = user.getRoles()
                .stream()
                .toList();

        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES(?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, user.getId());
                        ps.setString(2, roles.get(i).name());
                    }

                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }
}
