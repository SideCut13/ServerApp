package pl.zdrojewska.service;

import pl.zdrojewska.dao.ClientDao;
import pl.zdrojewska.dao.ClientDaoImpl;
import pl.zdrojewska.dao.MealDao;
import pl.zdrojewska.dao.MealDaoImpl;
import pl.zdrojewska.tables.Client;
import pl.zdrojewska.tables.Meal;

import java.util.List;
import java.util.Optional;

public class ServiceImpl implements Service {
    private ClientDao clientDao = new ClientDaoImpl();
    private MealDao mealDao = new MealDaoImpl();
    @Override
    public void addClientService(String login, String password) {
        clientDao.addClient(Client
                .builder()
                .login(login)
                .password(password)
                .build());
    }

    @Override
    public void updateClientService(Long id, String login, String password) {
        Optional<Client> clientOp = clientDao.getClientById(id);
        if (clientOp.isPresent())
        {
            Client client = clientOp.get();
            client.setLogin(login);
            client.setPassword(password);
            clientDao.updateClient(client);
        }
    }

    @Override
    public void deleteClientService(Long id) {
        clientDao.deleteClient(id);
    }

    @Override
    public List<Client> getAllClientsService() {
        List<Client> clients = clientDao.getAllClients();
        return clients;
    }

    @Override
    public void addMealService(String title, String summary, String description, String date, String imagePath, Long clientId) {
        Optional<Client> clientOp = clientDao.getClientById(clientId);
        if(clientOp.isPresent()){
            mealDao.addMeal(
                    Meal
                    .builder()
                    .title(title)
                    .summary(summary)
                    .description(description)
                    .date(date)
                    .imagePath(imagePath)
                    .client(clientOp.get())
                    .build());
        }
    }

    @Override
    public void updateMealService(Long id, String title, String summary, String description, String date, String imagePath, Long clientId) {
        Optional<Meal> mealOp = mealDao.getMealById(id);
        Optional<Client> clientOp = clientDao.getClientById(clientId);
        if(mealOp.isPresent()){
            Meal meal = mealOp.get();
            meal.setTitle(title);
            meal.setSummary(summary);
            meal.setDescription(description);
            meal.setDate(date);
            meal.setImagePath(imagePath);
            meal.setClient(clientOp.get());
            mealDao.updateMeal(meal);
        }
    }

    @Override
    public void deleteMealService(Long id) {
        mealDao.deleteMeal(id);
    }

    @Override
    public List<Meal> getAllMealsService() {
        List<Meal> meals = mealDao.getAllMeals();
        return meals;
    }
}
