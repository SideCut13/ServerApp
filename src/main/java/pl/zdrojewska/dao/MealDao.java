package pl.zdrojewska.dao;

import pl.zdrojewska.tables.Meal;

import java.util.List;
import java.util.Optional;

public interface MealDao {
    void addMeal(Meal meal);
    void updateMeal(Meal meal);
    void deleteMeal(Long id);
    Optional<Meal> getMealById(Long id);
    List<Meal> getAllMeals();
}
