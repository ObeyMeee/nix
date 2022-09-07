package ua.com.andromeda.repository.mongodb;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Decimal128;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.MongoDbConfig;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.utils.mapper.InvoiceMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

@Singleton
public class InvoiceRepositoryMongoDbImpl {
    public static final String COLLECTION_NAME = "invoices";
    private final MongoCollection<Document> invoices;

    @Autowired
    public InvoiceRepositoryMongoDbImpl(MongoDbConfig mongoDbConfig) {
        MongoDatabase database = mongoDbConfig.getMongoDatabase("test_mongodb");
        database.drop();
        invoices = database.getCollection(COLLECTION_NAME);
    }

    public boolean save(Invoice invoice) {
        Document document = InvoiceMapper.mapInvoiceToDocument(invoice);
        invoices.insertOne(document);
        return true;
    }

    public Invoice getById(String id) {
        Document document = invoices.find(eq("_id", id)).first();
        if (document == null) {
            return null;
        }
        return InvoiceMapper.mapDocumentToInvoice(document);
    }

    public List<Invoice> getInvoicesWhereTotalPriceGreaterThan(BigDecimal totalPrice) {
        Bson filter = gt("totalPrice", totalPrice);
        return invoices.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(InvoiceMapper::mapDocumentToInvoice)
                .toList();
    }

    public int getInvoicesCount() {
        return (int) invoices.countDocuments();
    }

    public void updateTime(String id) {
        final Document filter = new Document();
        filter.append("_id", id);
        final Document newData = new Document();
        newData.append("created", LocalDateTime.now());
        final Document updateObject = new Document();
        updateObject.append("$set", newData);
        invoices.updateOne(filter, updateObject);
    }

    public Map<BigDecimal, Integer> groupByTotalPrice() {
        HashMap<BigDecimal, Integer> map = new HashMap<>();
        AggregateIterable<Document> documents =
                invoices.aggregate(List.of(group("$totalPrice", Accumulators.sum("count", 1))));

        for (Document document : documents) {
            BigDecimal totalPrice = ((Decimal128) document.get("_id")).bigDecimalValue();
            Integer count = document.getInteger("count");
            map.put(totalPrice, count);
        }
        return map;
    }
}
