package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MealDaoImpl implements MealDao {

    public static Integer UUID = 0;
    static ReentrantLock counterLock = new ReentrantLock(true);

    public static ConcurrentHashMap<Integer, Meal> meals;

    {
        meals.put(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(3, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(4, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(5, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(6, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(7, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(8, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }


    public MealDaoImpl() {
    }

    static void incrementUUID() {
        counterLock.lock();

        try {
            UUID++;
        } finally {
            counterLock.unlock();
        }
    }

    @Override
    public void createMeal(Meal meal) {
        if (!meals.contains(meal)) {
            incrementUUID();
            meal.setId(UUID);
            meals.put(UUID, meal);
        }
    }

    @Override
    public void deleteMeal(Integer UUID) {
        Meal meal = meals.get(UUID);
        if (meal != null) meals.remove(UUID, meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        incrementUUID();
        meals.put(UUID, meal);
    }

    @Override
    public List<Meal> getAllMeal() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getMealById(Integer UUID) {
        return meals.getOrDefault(UUID, null);
    }
}
