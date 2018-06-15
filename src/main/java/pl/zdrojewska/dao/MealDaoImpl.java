package pl.zdrojewska.dao;

import pl.zdrojewska.tables.Client;
import pl.zdrojewska.tables.Meal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class MealDaoImpl implements MealDao{
    private EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getFactory();
    private ClientDao clientDao = new ClientDaoImpl();

    @Override
    public void addMeal(Meal meal) {
        if (meal == null)
        {
            return;
        }
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {
            tx.begin();
            Client client = clientDao.getClientById(meal.getClient().getId()).get();
            meal.setClient(client);
            em.merge(meal);
            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            if(em != null) {
                em.close();
            }
        }
    }

    @Override
    public void updateMeal(Meal meal) {
        if (meal == null)
        {
            return;
        }

        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try
        {
            tx.begin();
            Meal mealDb = em.find(Meal.class, meal.getId());
            Client client = em.find(Client.class, meal.getClient().getId());
            mealDb.setTitle(meal.getTitle());
            mealDb.setSummary(meal.getSummary());
            mealDb.setDescription(meal.getDescription());
            mealDb.setDate(meal.getDate());
            mealDb.setImagePath(meal.getImagePath());
            mealDb.setClient(client);
            em.merge(mealDb);
            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            if(em != null) {
                em.close();
            }
        }
    }

    @Override
    public void deleteMeal(Long id) {
        if (id == null)
        {
            return;
        }
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {
            tx.begin();
            Meal meal = em.find(Meal.class, id);
            em.remove(meal);
            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
        }
        finally {
            if(em != null) {
                em.close();
            }
        }
    }

    @Override
    public Optional<Meal> getMealById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Optional<Meal> mealOp = Optional.empty();

        try
        {
            tx.begin();
            Meal meal = em.find(Meal.class, id);
            mealOp = Optional.ofNullable(meal);
            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
        }
        finally {
            if(em != null) {
                em.close();
            }
        }
        return mealOp;
    }

    @Override
    public List<Meal> getAllMeals() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        List<Meal> meals = null;
        try
        {
            tx.begin();

            Query query = em.createQuery("select m from Meal m");
            meals = query.getResultList();

            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
        }
        finally {
            if(em != null) {
                em.close();
            }
        }
        return meals;
    }
}
