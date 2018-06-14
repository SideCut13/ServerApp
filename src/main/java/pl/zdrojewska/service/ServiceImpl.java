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
    public void addMeal(String title, String summary, String description, String date, String imagePath) {
        mealDao.addMeal(Meal
                .builder()
                .title(title)
                .summary(summary)
                .description(description)
                .date(date)
                .imagePath(imagePath).build());
    }

    @Override
    public void updateMeal(Long id, String title, String summary, String description, String date, String imagePath) {
        Optional<Meal> mealOp = mealDao.getMealById(id);
        if(mealOp.isPresent()){
            Meal meal = mealOp.get();
            meal.setTitle(title);
            meal.setSummary(summary);
            meal.setDescription(description);
            meal.setDate(date);
            meal.setImagePath(imagePath);
            mealDao.updateMeal(meal);
        }
    }

    @Override
    public void deleteMeal(Long id) {
        mealDao.deleteMeal(id);
    }

    @Override
    public List<Meal> getAllMealsService() {
        List<Meal> meals = mealDao.getAllMeals();
        return meals;
    }
}
