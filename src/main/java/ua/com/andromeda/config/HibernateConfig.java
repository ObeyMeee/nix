package ua.com.andromeda.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.model.Detail;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.model.cars.Truck;
import ua.com.andromeda.model.cars.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
@Getter
public class HibernateConfig {
    private final SessionFactory sessionFactory;
    private final EntityManager entityManager;

    public HibernateConfig() {
        this.sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Auto.class)
                .addAnnotatedClass(Detail.class)
                .addAnnotatedClass(Engine.class)
                .addAnnotatedClass(SportCar.class)
                .addAnnotatedClass(Truck.class)
                .addAnnotatedClass(Vehicle.class)
                .addAnnotatedClass(Invoice.class)
                .buildSessionFactory();

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("persistence");

        this.entityManager = entityManagerFactory.createEntityManager();
    }
}
