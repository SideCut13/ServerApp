package pl.zdrojewska.service;

import pl.zdrojewska.tables.Client;
import pl.zdrojewska.tables.Meal;

import java.util.List;

public interface Service {
    void addClientService(String login, String password);
    void updateClientService(Long id, String login, String password);
    void deleteClientService(Long id);
    List<Client> getAllClientsService();
    void addMealService(String title, String summary, String description, String date, String imagePath, Long clientId);
    void updateMealService(Long id, String title, String summary, String description, String date, String imagePath, Long clientId);
    void deleteMealService(Long id);
    List<Meal> getAllMealsService();
}
