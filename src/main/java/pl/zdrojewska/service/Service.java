package pl.zdrojewska.service;

import pl.zdrojewska.tables.Client;
import pl.zdrojewska.tables.Meal;

import java.util.List;

public interface Service {
    void addClientService(String login, String password);
    void updateClientService(Long id, String login, String password);
    void deleteClientService(Long id);
    List<Client> getAllClientsService();
    void addMeal(String title, String summary, String description, String date, String imagePath);
    void updateMeal(Long id, String title, String summary, String description, String date, String imagePath);
    void deleteMeal(Long id);
    List<Meal> getAllMealsService();
}
