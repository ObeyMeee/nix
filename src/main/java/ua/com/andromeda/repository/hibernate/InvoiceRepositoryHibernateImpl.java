package ua.com.andromeda.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.HibernateConfig;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.model.dto.InvoiceDTO;
import ua.com.andromeda.repository.InvoiceRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class InvoiceRepositoryHibernateImpl implements InvoiceRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public InvoiceRepositoryHibernateImpl(HibernateConfig hibernateConfig) {
        this.sessionFactory = hibernateConfig.getSessionFactory();
    }

    @Override
    public boolean save(Invoice invoice) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(invoice);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Invoice> getInvoicesWhereTotalPriceGreaterThan(BigDecimal totalPrice) {
        Map<String, BigDecimal> totalPricePerInvoice = groupByTotalPrice();
        return totalPricePerInvoice.entrySet()
                .stream()
                .filter(entry -> entry.getValue().compareTo(totalPrice) > 0)
                .map(entry -> getById(entry.getKey()))
                .toList();
    }

    public Invoice getById(String id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Invoice invoice = session.get(Invoice.class, id);
        transaction.commit();
        return invoice;
    }

    @Override
    @Transactional
    public int getInvoicesCount() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        int count = (Integer) session.createSQLQuery("select count(*) as count from Invoice")
                .addScalar("count", IntegerType.INSTANCE).uniqueResult();
        transaction.commit();
        session.close();
        return count;
    }

    @Override
    public void updateTime(String id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Invoice invoice = session.get(Invoice.class, id);
        invoice.setCreated(LocalDateTime.now());
        session.update(invoice);
        transaction.commit();
        session.close();
    }

    @Override
    public Map<String, BigDecimal> groupByTotalPrice() {
        Map<String, BigDecimal> totalPricePerInvoice = new HashMap<>();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("select i.id as invoiceId, sum(a.price * a.count) as totalPrice " +
                        "from Invoice as i " +
                        "join Vehicle as v on v.invoice.id = i.id " +
                        "join Auto as a on v.id = a.id " +
                        "group by i.id")
                .setResultTransformer(Transformers.aliasToBean(InvoiceDTO.class))
                .list()
                .forEach(i -> {
                    InvoiceDTO invoiceDTO = (InvoiceDTO) i;
                    totalPricePerInvoice.put(invoiceDTO.getInvoiceId(), invoiceDTO.getTotalPrice());
                });
        transaction.commit();
        session.close();
        return totalPricePerInvoice;
    }
}
