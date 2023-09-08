package org.example;

import org.example.entities.Client;
import org.example.entities.Planet;
import org.example.services.ClientCrudService;
import org.example.services.PlanetCrudService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        ClientCrudService clientCrudService = new ClientCrudService(sessionFactory);
        PlanetCrudService planetCrudService = new PlanetCrudService(sessionFactory);

        Client newClient = new Client("John Doe");
        clientCrudService.createClient(newClient);

        Planet newPlanet = new Planet("MARS", "Mars");
        planetCrudService.createPlanet(newPlanet);

        newClient.setName("Jane Smith");
        clientCrudService.updateClient(newClient);

        Client retrievedClient = clientCrudService.readClient(newClient.getId());
        System.out.println("Retrieved Client: " + retrievedClient);

        try (Session session = sessionFactory.openSession()) {
            List<Planet> planets = session.createQuery("FROM Planet", Planet.class).list();
            System.out.println("All Planets:");
            for (Planet planet : planets) {
                System.out.println(planet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        clientCrudService.deleteClient(newClient.getId());
        planetCrudService.deletePlanet(newPlanet.getId());

        HibernateUtil.getInstance().close();
    }
}
