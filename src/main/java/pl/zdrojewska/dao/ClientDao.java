package pl.zdrojewska.dao;

import pl.zdrojewska.tables.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    void addClient(Client client);
    void updateClient(Client client);
    void deleteClient(Long id);
    Optional<Client> getClientById(Long id);
    List<Client> getAllClients();

}
