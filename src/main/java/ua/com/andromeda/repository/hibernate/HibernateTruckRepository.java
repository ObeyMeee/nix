package ua.com.andromeda.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.HibernateConfig;
import ua.com.andromeda.model.cars.Truck;
import ua.com.andromeda.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Singleton
public class HibernateTruckRepository implements CrudRepository<Truck> {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateTruckRepository(HibernateConfig hibernateConfig) {
        sessionFactory = hibernateConfig.getSessionFactory();
    }

    @Override
    public Optional<Truck> findById(String id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Truck truck = session.get(Truck.class, id);
        transaction.commit();
        session.close();
        return Optional.of(truck);
    }

    @Override
    public List<Truck> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Truck> trucks = session.createQuery("from Truck ", Truck.class).list();
        transaction.commit();
        session.close();
        return trucks;
    }

    @Override
    public boolean save(Truck vehicle) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(vehicle);
        System.out.println("vehicle = " + vehicle);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public void saveAll(List<Truck> vehicles) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        vehicles.forEach(session::persist);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Truck vehicle) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(vehicle);
        transaction.commit();
        session.close();
    }

    @Override
    public boolean delete(String id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Truck truck = session.get(Truck.class, id);
        session.remove(truck);
        transaction.commit();
        session.close();
        return true;
    }
}
