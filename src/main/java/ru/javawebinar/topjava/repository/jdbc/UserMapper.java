package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Polik on 3/22/2022
 */
public class UserMapper implements RowMapper<List<User>> {
    @Override
    public List<User> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<Integer, User> roles = new HashMap<>();

        do {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            String password = rs.getString(4);
            Date registered = new Date(rs.getTimestamp(5).getTime());
            boolean enabled = rs.getBoolean(6);
            int calories = rs.getInt(7);
            Role role = Enum.valueOf(Role.class, rs.getString(9));

            User user = new User(id, name, email, password, calories, enabled, registered, Collections.singleton(role));
            roles.merge(id, user, (oldUser, newUser) -> {
                newUser.getRoles().addAll(oldUser.getRoles());
                return newUser;
            });
        } while (rs.next());
        {
        }

        return roles.values()
                .stream()
                .toList();
    }
}
