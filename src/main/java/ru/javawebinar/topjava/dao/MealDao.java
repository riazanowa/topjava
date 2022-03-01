package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void createMeal(Meal meal);
    void deleteMeal(Integer UUID);
    void updateMeal(Meal meal);
    List<Meal> getAllMeal();
    Meal getMealById(Integer UUID);
}
