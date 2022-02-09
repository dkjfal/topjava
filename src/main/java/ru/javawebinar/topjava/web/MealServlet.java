package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Polik on 2/3/2022
 */
public class MealServlet extends HttpServlet {
    private final MealDAO dao = new MealDAOImpl();
    private final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        switch (request.getRequestURI()) {
            case "/topjava/meals":
                request.setAttribute("meals", MealsUtil.filteredByStreams(dao.getAll()
                        , null, null, 2000));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "/topjava/meals/edit":
                Integer id = request.getParameter("id") == null ? null :
                        Integer.parseInt(request.getParameter("id"));
                Meal meal;
                if (id != null) {
                    meal = dao.getAll().get(id-1);
                } else {
                    meal = new Meal();
                }

                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/edit-meal.jsp").forward(request, response);
                break;
            case "/topjava/meals/delete":
                id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);

                response.sendRedirect("/topjava/meals");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal();

        meal.setId(Objects.equals(request.getParameter("id"), "") ? null :
                Integer.parseInt(request.getParameter("id")));
        meal.setDescription(request.getParameter("description"));
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));

        dao.save(meal);

        response.sendRedirect("/topjava/meals");
    }
}
