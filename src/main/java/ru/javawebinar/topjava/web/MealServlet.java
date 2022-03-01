package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    public MealDao mealDao;

    private static String INSERT_OR_EDIT = "/main/webapp/meal_insert_or_edit.jsp";
    private static String MEAL_LIST = "/main/webapp/meals.jsp";

    public MealServlet() {
        super();
        this.mealDao = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<MealTo> mealToList = MealsUtil.mapToMealto(MealsUtil.MEALS);
        req.setAttribute("mealToList", mealToList);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(request.getParameter("datetime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));

        mealDao.createMeal(meal);

        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST);
        List<MealTo> mealToList = MealsUtil.mapToMealto(mealDao.getAllMeal());
        request.setAttribute("mealToList",mealToList );
        view.forward(request, response);
    }

}
