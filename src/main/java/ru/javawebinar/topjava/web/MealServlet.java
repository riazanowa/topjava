package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
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

    private static final String INSERT_OR_EDIT = "/mealInsertOrEdit.jsp";
    private static final String MEAL_LIST = "/meals.jsp";
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private static final int stubForCaloriesPerDay = 2000;

    public MealDao mealDao;


    @Override
    public void init() throws ServletException {
        this.mealDao = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");

        if (action == null) action = "listMeals";

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            mealDao.delete(mealId);
            log.info("Meal {} has been deleted", mealId);
            forward = MEAL_LIST;
            List<MealTo> mealToList = MealsUtil.filteredByStreams(mealDao.getAll(), stubForCaloriesPerDay);
            req.setAttribute("mealToList", mealToList);
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealDao.getById(mealId);
            log.info("Meal with id {} has been got", meal.getId());
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeals")) {
            forward = MEAL_LIST;
            List<MealTo> mealToList = MealsUtil.filteredByStreams(mealDao.getAll(), stubForCaloriesPerDay);
            req.setAttribute("mealToList", mealToList);
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
        log.info("Forward to {}", forward);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("datetime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            mealDao.create(meal);
            log.info("{} has been created", meal.toString());
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealDao.update(meal);
            log.info("{} has been updated", meal.toString());
        }

        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST);
        List<MealTo> mealToList = MealsUtil.filteredByStreams(mealDao.getAll(), stubForCaloriesPerDay);
        request.setAttribute("mealToList", mealToList);
        view.forward(request, response);
        log.info("Forward to {}", MEAL_LIST);
    }
}
