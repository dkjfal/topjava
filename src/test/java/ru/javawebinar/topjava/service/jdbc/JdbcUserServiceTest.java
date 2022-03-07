package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

/**
 * Created by Polik on 3/7/2022
 */
@ActiveProfiles("jdbc")
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void create() {
        super.create();
    }

    @Test
    public void duplicateMailCreate() {
        super.duplicateMailCreate();
    }

    @Test
    public void delete() {
        super.delete();
    }

    @Test
    public void deletedNotFound() {
        super.deletedNotFound();
    }

    @Test
    public void get() {
        super.get();
    }

    @Test
    public void getNotFound() {
        super.getNotFound();
    }

    @Test
    public void getByEmail() {
        super.getByEmail();
    }

    @Test
    public void update() {
        super.update();
    }

    @Test
    public void getAll() {
        super.getAll();
    }
}
