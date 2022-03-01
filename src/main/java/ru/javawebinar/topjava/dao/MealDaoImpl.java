package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DBConfiguration;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MealDaoImpl implements MealDao {
    private Connection connection;

    public static List<Meal> meals;

    {
        meals = Collections.synchronizedList(Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
    }


    public MealDaoImpl() {
        this.connection = DBConfiguration.getConnection();
    }

    @Override
    public void createMeal(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO meals(datetime, description, calories) values (?, ?, ?)");
            // Parameters start with 1
            preparedStatement.setTimestamp(1, Timestamp.valueOf(meal.getDateTime()));
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3, meal.getCalories());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMeal(Integer UUID) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM meals where meal_id=?");
            preparedStatement.setInt(1, UUID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMeal(Meal meal) {
//        try {
//            PreparedStatement preparedStatement = connection
//                    .prepareStatement("UPDATE meals set datetime=?, description=?, calories=?" +
//                            "where meal_id=?");
//            preparedStatement.setTimestamp(1, Timestamp.valueOf(meal.getDateTime()));
//            preparedStatement.setString(2, meal.getDescription());
//            preparedStatement.setInt(3, meal.getCalories());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public List<Meal> getAllMeal() {
        List<Meal> meals = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM meals");
            while (rs.next()) {
                Meal meal = new Meal();
                meal.setUUID(rs.getInt("meal_id"));
                meal.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());
                meal.setDescription(rs.getString("description"));
                meal.setCalories(rs.getInt("calories"));
                meals.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    @Override
    public Meal getMealById(Integer UUID) {
        Meal meal = new Meal();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from meals where meal_id=?");
            preparedStatement.setInt(1, UUID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                meal.setUUID(rs.getInt("meal_id"));
                meal.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());
                meal.setDescription(rs.getString("description"));
                meal.setCalories(rs.getInt("calories"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meal;
    }
}
