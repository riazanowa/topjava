package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
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
import java.util.Locale;

public class MealServlet extends HttpServlet {

    private static final String ADD_OR_EDIT = "/addOrEditMeal.jsp";
    private static final String MEAL_LIST = "/meals.jsp";
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        this.mealDao = new InMemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");
        int mealId;

        if (action == null) action = "listMeals";

        switch (action) {
            case ("delete"):
                mealId = Integer.parseInt(req.getParameter("mealId"));
                mealDao.delete(mealId);
                log.info("Meal {} has been deleted", mealId);
                resp.sendRedirect(req.getContextPath() + "/meals");
                return;
            case ("edit"):
                mealId = Integer.parseInt(req.getParameter("mealId"));
                forward = ADD_OR_EDIT;
                Meal meal = mealDao.getById(mealId);
                log.info("Meal with id {} has been got", meal.getId());
                req.setAttribute("meal", meal);
                break;
            case ("add"):
                forward = ADD_OR_EDIT;
                Meal newMeal = new Meal(
                       LocalDateTime.parse(DateTimeUtil.getCurrentFormattedTime()),
                        "Беляши",
                        700);
                req.setAttribute("meal", newMeal);
                break;
            case ("listMeals"):
            default:
                forward = MEAL_LIST;
                List<MealTo> mealToList = MealsUtil.getListWithExcess(mealDao.getAll(), MealsUtil.CALORIES_PER_DAY);
                req.setAttribute("mealToList", mealToList);
                break;
        }

        req.getRequestDispatcher(forward).forward(req, resp);
        log.info("Forward to {}", forward);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("datetime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            mealDao.add(meal);
            log.info("{} has been created", meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealDao.update(meal);
            log.info("{} has been updated", meal);
        }

        log.info("Redirect to Meal Servlet");
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
