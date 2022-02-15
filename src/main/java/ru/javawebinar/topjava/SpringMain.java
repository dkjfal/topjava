package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            UserRepository repository = appCtx.getBean(UserRepository.class);
            MealRestController controller = appCtx.getBean(MealRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
//            System.out.println(repository.get(8));

            controller.create(new Meal(LocalDateTime.parse("2022-02-02T15:43"), "Description", 1337));
            controller.create(new Meal(LocalDateTime.parse("2022-02-02T15:41"), "Description", 1337));
            controller.create(new Meal(LocalDateTime.parse("2022-01-16T20:00"), "Description", 1337));
            controller.create(new Meal(LocalDateTime.parse("2022-01-13T13:00"), "Description", 1337));
            controller.create(new Meal(LocalDateTime.parse("2022-01-09T05:00"), "Description", 1337));
            controller.create(new Meal(LocalDateTime.parse("2021-12-28T10:57"), "Description", 1337));
            controller.create(new Meal(LocalDateTime.parse("2021-12-09T13:34"), "Description", 1337));
//            controller.create(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 12, 10, 3), "Description", 1337));

            List<MealTo> meals = controller.getAll();
            List<MealTo> meals1 = controller.getAllBetweenSelectedDate(LocalDateTime.parse("2021-12-28T10:57"), LocalDateTime.parse("2022-02-02T15:41"));

            System.out.println(DateTimeUtil.toString(LocalDateTime.parse("2022-02-02T15:41")));

            meals1.forEach(System.out::println);
        }
    }
}
