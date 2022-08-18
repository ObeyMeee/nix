package ua.com.andromeda.module2.service;

import org.apache.commons.text.CaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.module2.entity.*;
import ua.com.andromeda.module2.exceptions.LineFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);
    private static final PersonService PERSON_SERVICE = PersonService.getInstance();
    private static final Random RANDOM = new Random();
    private static final BufferedReader FILE_READER;
    private static final String[] FIELD_NAMES;
    private static final List<String> productsListAsString;
    private static ShopService instance;

    static {
        String fileName = "products.csv";
        FILE_READER = getBufferedReader(fileName);
        try {
            FIELD_NAMES = getFieldNames();
            productsListAsString = getProductsListAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Invoice> invoices;

    private ShopService() {

    }

    private static BufferedReader getBufferedReader(String fileName) {
        InputStream inputStream = getInputStream(fileName);
        assert inputStream != null;
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private static InputStream getInputStream(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    private static String[] getFieldNames() throws IOException {
        String header = FILE_READER.readLine();
        String[] properties = header.split(",");
        for (int i = 0; i < properties.length; i++) {
            properties[i] = CaseUtils.toCamelCase(properties[i], false, ' ');
        }
        return properties;
    }

    private static List<String> getProductsListAsString() throws IOException {
        List<String> products = new ArrayList<>();
        String line = FILE_READER.readLine();
        while (line != null) {
            products.add(line);
            line = FILE_READER.readLine();
        }
        return products;
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
            Product product;
            switch (type) {
                case "Television" -> product = new Television();
                case "Telephone" -> product = new Telephone();
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }
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
                for (int i = 0; i < FIELD_NAMES.length; i++) {
                    final int finalI = i;
                    fields.stream()
                            .filter(field -> field.getName().equals(FIELD_NAMES[finalI]))
                            .forEach(field -> setTelephoneField(fieldValues, product, field, finalI));
                }
            }
            case "Television" -> {
                for (int i = 0; i < FIELD_NAMES.length; i++) {
                    final int finalI = i;
                    fields.stream()
                            .filter(field -> field.getName().equals(FIELD_NAMES[finalI]))
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
        BigDecimal summaryPrice = calculateTotalPriceForInvoice(products);
        if (summaryPrice.compareTo(limit) > 0) {
            return InvoiceType.WHOLESALE;
        }
        return InvoiceType.RETAIL;

    }

    public BigDecimal calculateTotalPriceForInvoice(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .map(this::calculateTotalPricePerProduct)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    private BigDecimal calculateTotalPricePerProduct(Map.Entry<Product, Integer> productIntegerEntry) {
        BigDecimal pricePerUnit = productIntegerEntry.getKey().getPrice();
        int quantity = productIntegerEntry.getValue();
        return pricePerUnit.multiply(new BigDecimal(quantity));
    }


    public void saveInvoice(Invoice invoice) {
        if (invoices == null) {
            invoices = new LinkedList<>();
        }
        LOGGER.info("[{}] [{}] [{}]", invoice.getCustomer(), invoice.getProducts(), invoice.getType());
        invoices.add(invoice);
    }

    public int getAmountSoldTelephones() {
        return invoices.stream()
                .flatMap(invoice -> invoice.getProducts().entrySet().stream())
                .filter(productIntegerEntry -> productIntegerEntry.getKey() instanceof Telephone)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public int getAmountSoldTelevisions() {
        return invoices.stream()
                .flatMap(invoice -> invoice.getProducts().entrySet().stream())
                .filter(productIntegerEntry -> productIntegerEntry.getKey() instanceof Television)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public Invoice getInvoiceBySmallestTotalPrice() {
        return invoices.stream()
                .min((invoice1, invoice2) -> {
                    BigDecimal totalPriceForInvoice1 = calculateTotalPriceForInvoice(invoice1.getProducts());
                    BigDecimal totalPriceForInvoice2 = calculateTotalPriceForInvoice(invoice2.getProducts());
                    return totalPriceForInvoice1.compareTo(totalPriceForInvoice2);
                })
                .orElseThrow();
    }

    public BigDecimal getTotalPriceForAllPurchases() {
        return invoices.stream()
                .flatMap(invoice -> invoice.getProducts().entrySet().stream())
                .map(this::calculateTotalPricePerProduct)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public long getAmountInvoicesWhereTypeEqualsRetail() {
        return invoices.stream()
                .filter(invoice -> invoice.getType().equals(InvoiceType.RETAIL))
                .count();
    }

    public List<Invoice> getInvoicesContainsOneProductType() {
        return invoices.stream()
                .filter(invoice -> {
                    Set<Product> products = invoice.getProducts().keySet();
                    boolean isAllProductsTelephones = products.stream().allMatch(Telephone.class::isInstance);
                    boolean isAllProductsTelevisions = products.stream().allMatch(Television.class::isInstance);
                    return isAllProductsTelephones || isAllProductsTelevisions;
                }).toList();
    }

    public List<Invoice> getFirstInvoices(int count) {
        return invoices.subList(0, count);
    }

    public List<Invoice> getInvoicesWhereCustomerIsUnderage() {
        return invoices.stream()
                .filter(invoice -> invoice.getCustomer().getAge() < 18)
                .peek(invoice -> invoice.setType(InvoiceType.LOW_AGE))
                .toList();
    }

    public void sort() {
        Comparator<Invoice> comparatorByCustomerAgeDesc = Comparator.comparingInt(o -> o.getCustomer().getAge());
        Comparator<Invoice> comparatorByTotalQuantity = Comparator.comparingInt(invoice -> invoice.getProducts()
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum());

        Comparator<Invoice> comparatorByTotalPrice = (o1, o2) -> calculateTotalPriceForInvoice(o1.getProducts())
                .compareTo(calculateTotalPriceForInvoice(o2.getProducts()));

        Comparator<Invoice> generalComparator = comparatorByCustomerAgeDesc
                .thenComparing(comparatorByTotalQuantity)
                .thenComparing(comparatorByTotalPrice);

        invoices.sort(generalComparator);
    }
}
