package ua.com.andromeda.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.HibernateConfig;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Singleton
public class HibernateSportCarRepository implements CrudRepository<SportCar> {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateSportCarRepository(HibernateConfig hibernateConfig) {
        sessionFactory = hibernateConfig.getSessionFactory();
    }

    @Override
    public Optional<SportCar> findById(String id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        SportCar sportCar = session.get(SportCar.class, id);
        transaction.commit();
        session.close();
        return Optional.of(sportCar);
    }

    @Override
    public List<SportCar> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<SportCar> sportCars = session.createQuery("from SportCar ", SportCar.class).list();
        transaction.commit();
        session.close();
        return sportCars;
    }

    @Override
    public boolean save(SportCar vehicle) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(vehicle);
        System.out.println("vehicle = " + vehicle);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public void saveAll(List<SportCar> vehicles) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        vehicles.forEach(session::persist);
        session.close();
        transaction.commit();
    }

    @Override
    public void update(SportCar vehicle) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(vehicle);
        session.close();
        transaction.commit();
    }

    @Override
    public boolean delete(String id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        SportCar sportCar = session.get(SportCar.class, id);
        session.remove(sportCar);
        transaction.commit();
        session.close();
        return true;
    }
}
