package ru.javawebinar.topjava.web;

import com.sun.istack.internal.Nullable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.web.SecurityUtil.*;
import static ru.javawebinar.topjava.util.DateTimeUtil.*;

/**
 * Created by Polik on 2/3/2022
 */
public class MealServlet extends HttpServlet {
    private MealRestController controller;
    private ConfigurableApplicationContext appCtx;


    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    public void destroy() {
        appCtx.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        switch (request.getRequestURI()) {
            case "/topjava/meals":
                if (request.getParameterMap().size() > 0) {
                    LocalDate startDate = parseLocalDate(request.getParameter("startdate"), true);
                    LocalDate enddate = parseLocalDate(request.getParameter("enddate"), false);
                    LocalTime starttime = parseLocalTime(request.getParameter("starttime"), true);
                    LocalTime endtime = parseLocalTime(request.getParameter("endtime"), false);

                    LocalDateTime from = convertToLocalDateTime(startDate, starttime);
                    LocalDateTime to = convertToLocalDateTime(enddate, endtime);

                    request.setAttribute("meals", controller.getAllBetweenSelectedDate(from, to));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                } else {
                    request.setAttribute("meals", controller.getAll());
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                }
                break;
            case "/topjava/meals/edit":
                Integer id = getId(request);
                Meal meal;
                if (id != null) {
                    meal = controller.get(id);
                } else {
                    meal = new Meal();
                }

                meal.setUserId(authUserId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/edit-meal.jsp").forward(request, response);
                break;
            case "/topjava/meals/delete":
                id = Integer.parseInt(request.getParameter("id"));
                controller.delete(id);

                response.sendRedirect("/topjava/meals");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal();

        meal.setId(getId(request));
        meal.setDescription(request.getParameter("description"));
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        meal.setUserId(Integer.parseInt(request.getParameter("userId")));

        if (meal.isNew())
            controller.create(meal);
        else
            controller.update(meal);

        response.sendRedirect("/topjava/meals");
    }

    @Nullable
    private Integer getId(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id == null || id.equals(""))
            return null;
        return Integer.valueOf(id);
    }
}
