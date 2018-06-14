package pl.zdrojewska.dao;

import pl.zdrojewska.tables.Client;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao{
    private EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getFactory();

    @Override
    public void addClient(Client client) {
        if(client == null){
            return;
        }
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            em.persist(client);
            tx.commit();
        }
        catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
        }
        finally {
            if(em != null){
                em.close();
            }
        }
    }

    @Override
    public void updateClient(Client client) {
        if(client == null){
            return;
        }
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try
        {
            tx.begin();
            Client clientDb = em.find(Client.class, client.getId());
            clientDb.setLogin(client.getLogin());
            clientDb.setPassword(client.getPassword());;
            em.persist(clientDb);
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
    public void deleteClient(Long id) {
        if (id == null)
        {
            return;
        }
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {
            tx.begin();
            Client client = em.find(Client.class, id);
            em.remove(client);
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
    public Optional<Client> getClientById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Optional<Client> clientOp = Optional.empty();

        try
        {
            tx.begin();

            Client client = em.find(Client.class, id);
            client.getMeals().size();
            clientOp = Optional.ofNullable(client);

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
        return clientOp;
    }

    @Override
    public List<Client> getAllClients() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Client> clients = null;
        try
        {
            tx.begin();
            Query query = em.createQuery("select c from Client c");
            clients = query.getResultList();
            clients.forEach(k -> k.getMeals().size());
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
        return clients;
    }
}
