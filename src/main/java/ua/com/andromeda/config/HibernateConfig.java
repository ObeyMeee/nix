package ua.com.andromeda.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import ua.com.andromeda.annotations.Singleton;

import javax.persistence.EntityManager;

@Singleton
@Getter
public class HibernateConfig {
    private SessionFactory sessionFactory;
    private EntityManager entityManager;

    public HibernateConfig() {
//        this.sessionFactory = new Configuration()
//                .configure()
//                .addAnnotatedClass(Auto.class)
//                .addAnnotatedClass(Detail.class)
//                .addAnnotatedClass(Engine.class)
//                .addAnnotatedClass(SportCar.class)
//                .addAnnotatedClass(Truck.class)
//                .addAnnotatedClass(Vehicle.class)
//                .addAnnotatedClass(Invoice.class)
//                .buildSessionFactory();
//
//        EntityManagerFactory entityManagerFactory =
//                Persistence.createEntityManagerFactory("persistence");
//
//        this.entityManager = entityManagerFactory.createEntityManager();
    }
}
