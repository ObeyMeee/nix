package ua.com.andromeda.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.HibernateConfig;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Singleton
public class HibernateAutoRepository implements CrudRepository<Auto> {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateAutoRepository(HibernateConfig hibernateConfig) {
        sessionFactory = hibernateConfig.getSessionFactory();
    }

    @Override
    public Optional<Auto> findById(String id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Auto auto = session.get(Auto.class, id);
        transaction.commit();
        session.close();
        return Optional.of(auto);
    }

    @Override
    public List<Auto> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Auto> autos = session.createQuery("from Auto ").list();
        transaction.commit();
        session.close();
        return autos;
    }

    @Override
    public boolean save(Auto vehicle) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(vehicle);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public void saveAll(List<Auto> vehicles) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        vehicles.forEach(session::persist);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Auto vehicle) {
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
        Auto auto = session.get(Auto.class, id);
        session.remove(auto);
        transaction.commit();
        session.close();
        return true;
    }
}