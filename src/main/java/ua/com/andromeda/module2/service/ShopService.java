package ua.com.andromeda.module2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.module2.entity.*;
import ua.com.andromeda.module2.exceptions.LineFormatException;
import ua.com.andromeda.module2.utils.FileReaderUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ShopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);
    private static final PersonService PERSON_SERVICE = PersonService.getInstance();
    private static final Random RANDOM = new Random();
    private static ShopService instance;
    private final List<Invoice> invoices;
    private final List<String> productsListAsString;
    private final String[] fieldNames;
    private final ProductFactory productFactory = new ProductFactory();


    private ShopService() {
        invoices = new LinkedList<>();
        final FileReaderUtils fileReaderUtils = new FileReaderUtils();
        fieldNames = fileReaderUtils.getFieldNames();
        productsListAsString = fileReaderUtils.getProductsListAsString();
    }


    public static ShopService getInstance() {
        if (instance == null) {
            instance = new ShopService();
        }
        return instance;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public Invoice createRandomInvoice(BigDecimal limit) {
        Invoice invoice = new Invoice();

        Map<Product, Integer> products = generateProducts();
        invoice.setProducts(products);
        invoice.setCustomer(PERSON_SERVICE.getRandomCustomer());
        invoice.setType(getInvoiceType(products, limit));
        return invoice;
    }

    private Map<Product, Integer> generateProducts() {
        int[] productsIndexes = generateRandomProductsIndexes();
        Map<Product, Integer> products = new HashMap<>();

        for (int productsIndex : productsIndexes) {
            String productAsString = productsListAsString.get(productsIndex);
            productAsString = checkOnValid(productAsString, productsIndex);
            String[] fieldValues = productAsString.split(",");
            String type = fieldValues[0];
            Product product = productFactory.createProduct(ProductType.valueOf(type.toUpperCase()));
            setValues(fieldValues, product);
            products.merge(product, 1, Integer::sum);
        }
        return products;
    }

    private String checkOnValid(String productAsString, int productsIndex) {
        if (productAsString.matches(".+,,.+|^,.+|.+,$")) {
            try {
                throw new LineFormatException("Line " + (productsIndex + 2) + " in table is incorrect");
            } catch (LineFormatException e) {
                LOGGER.error(e.getMessage());
                System.err.println(e.getMessage());
                productAsString = productsListAsString.get(0);
            }
        }
        return productAsString;
    }

    private void setValues(String[] fieldValues, Product product) {
        List<Field> fields = getAllFields(product.getClass());
        switch (product.getClass().getSimpleName()) {
            case "Telephone" -> {
                for (int i = 0; i < fieldNames.length; i++) {
                    final int finalI = i;
                    fields.stream()
                            .filter(field -> field.getName().equals(fieldNames[finalI]))
                            .forEach(field -> setTelephoneField(fieldValues, product, field, finalI));
                }
            }
            case "Television" -> {
                for (int i = 0; i < fieldNames.length; i++) {
                    final int finalI = i;
                    fields.stream()
                            .filter(field -> field.getName().equals(fieldNames[finalI]))
                            .forEach(field -> setTelevisionField(fieldValues, product, field, finalI));
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + product.getClass().getSimpleName());
        }

    }

    private void setTelephoneField(String[] fieldValues, Product telephone, Field field, int i) {
        String fieldName = field.getName();
        field.setAccessible(true);
        try {
            if (fieldName.equals("price")) {
                field.set(telephone, new BigDecimal(fieldValues[i]));
            } else if (fieldName.equals("model")) {
                field.set(telephone, new Model(fieldValues[i]));
            } else {
                field.set(telephone, fieldValues[i]);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTelevisionField(String[] fieldValues, Product television, Field field, int i) {
        String fieldName = field.getName();
        field.setAccessible(true);
        try {
            if (fieldName.equals("diagonal")) {
                field.set(television, Integer.parseInt(fieldValues[i]));
            } else if (fieldName.equals("price")) {
                field.set(television, new BigDecimal(fieldValues[i]));
            } else {
                field.set(television, fieldValues[i]);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private int[] generateRandomProductsIndexes() {
        int amountProducts = RANDOM.nextInt(1, 6);
        int[] indexesDesiredProducts = new int[amountProducts];
        for (int i = 0; i < amountProducts; i++) {
            indexesDesiredProducts[i] = RANDOM.nextInt(productsListAsString.size());
        }
        return indexesDesiredProducts;
    }

    // https://www.baeldung.com/java-reflection-class-fields
    private List<Field> getAllFields(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
        result.addAll(List.of(clazz.getDeclaredFields()));
        return result;
    }

    private InvoiceType getInvoiceType(Map<Product, Integer> products, BigDecimal limit) {
        ShopStatistics shopStatistics = new ShopStatistics(this);
        BigDecimal summaryPrice = shopStatistics.calculateTotalPriceForInvoice(products);
        if (summaryPrice.compareTo(limit) > 0) {
            return InvoiceType.WHOLESALE;
        }
        return InvoiceType.RETAIL;

    }


    public void saveInvoice(Invoice invoice) {
        LOGGER.info("[{}] [{}] [{}]", invoice.getCustomer(), invoice.getProducts(), invoice.getType());
        invoices.add(invoice);
    }


    public void sort() {
        Comparator<Invoice> comparatorByCustomerAgeDesc = Comparator.comparingInt(o -> o.getCustomer().getAge());
        Comparator<Invoice> comparatorByTotalQuantity = Comparator.comparingInt(invoice -> invoice.getProducts()
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum());

        final ShopStatistics shopStatistics = new ShopStatistics(this);
        Comparator<Invoice> comparatorByTotalPrice =
                Comparator.comparing(o -> shopStatistics.calculateTotalPriceForInvoice(o.getProducts()));

        Comparator<Invoice> generalComparator = comparatorByCustomerAgeDesc
                .thenComparing(comparatorByTotalQuantity)
                .thenComparing(comparatorByTotalPrice);

        invoices.sort(generalComparator);
    }
}
