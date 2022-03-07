package ru.javawebinar.topjava.service.jpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

/**
 * Created by Polik on 3/7/2022
 */
@ActiveProfiles("jpa")
public class JpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    public void delete() {
        super.delete();
    }

    @Test
    public void deleteNotFound() {
        super.deleteNotFound();
    }

    @Test
    public void deleteNotOwn() {
        super.deleteNotOwn();
    }

    @Test
    public void duplicateDateTimeCreate() {
        super.duplicateDateTimeCreate();
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
    public void getNotOwn() {
        super.getNotOwn();
    }

    @Test
    public void update() {
        super.update();
    }

    @Test
    public void updateNotOwn() {
        super.updateNotOwn();
    }

    @Test
    public void getAll() {
        super.getAll();
    }

    @Test
    public void getBetweenInclusive() {
        super.getBetweenInclusive();
    }

    @Test
    public void getBetweenWithNullDates() {
        super.getBetweenWithNullDates();
    }

    @Test
    public void create() {
        super.create();
    }
}
