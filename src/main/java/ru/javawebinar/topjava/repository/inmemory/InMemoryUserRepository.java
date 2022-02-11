package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.jws.soap.SOAPBinding;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger lastId = new AtomicInteger(1);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        users.remove(id);
        return true;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew())
            user.setId(lastId.getAndIncrement());

        return users.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return users.values().stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            User user = entry.getValue();
            if (user.getEmail().equals(email))
                return user;
        }

        throw new NotFoundException("No such user with email: " + email);
    }
}
