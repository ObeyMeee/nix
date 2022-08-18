package ua.com.andromeda.module2.service;

import org.apache.commons.text.CaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.module2.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ShopService {

    private static ShopService instance;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);

    private static final PersonService PERSON_SERVICE = PersonService.getInstance();
    private static final Random RANDOM = new Random();
    private static final BufferedReader FILE_READER;
    private static final String[] FIELD_NAMES;
    private static final List<String> productsListAsString;

    private List<Invoice> invoices;

    private ShopService() {

    }

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

    public Invoice createRandomInvoice(BigDecimal limit) throws IllegalAccessException {
        Map<Product, Integer> products = generateProducts();

        Invoice invoice = new Invoice();
        invoice.setProducts(products);
        invoice.setCustomer(PERSON_SERVICE.getRandomCustomer());
        invoice.setType(getInvoiceType(products, limit));
        return invoice;
    }

    private Map<Product, Integer> generateProducts() throws IllegalAccessException {
        int[] productsIndexes = generateRandomProductsIndexes();
        Map<Product, Integer> products = new HashMap<>();

        for (int productsIndex : productsIndexes) {
            String productAsString = productsListAsString.get(productsIndex);
            String[] fieldValues = productAsString.split(",");
            String type = fieldValues[0];
            switch (type) {
                case "Television" -> {
                    Television television = new Television();
                    for (Field field : getAllFields(television.getClass())) {
                        String fieldName = field.getName();
                        for (int i = 0; i < FIELD_NAMES.length; i++) {
                            if (fieldName.equals(FIELD_NAMES[i])) {
                                setTelevisionFields(fieldValues, television, field, fieldName, i);
                                break;
                            }
                        }
                    }
                    products.merge(television, 1, Integer::sum);
                }
                case "Telephone" -> {
                    Telephone telephone = new Telephone();
                    for (Field field : getAllFields(telephone.getClass())) {
                        String fieldName = field.getName();
                        for (int i = 0; i < FIELD_NAMES.length; i++) {
                            if (fieldName.equals(FIELD_NAMES[i])) {
                                setTelephoneFields(fieldValues, telephone, field, fieldName, i);
                                break;
                            }
                        }
                    }
                    products.merge(telephone, 1, Integer::sum);
                }
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }
        }
        return products;
    }

    private int[] generateRandomProductsIndexes() {
        int amountProducts = RANDOM.nextInt(1, 6);
        int[] indexesDesiredProducts = new int[amountProducts];
        for (int i = 0; i < amountProducts; i++) {
            indexesDesiredProducts[i] = RANDOM.nextInt(11);
        }
        return indexesDesiredProducts;
    }

    private void setTelephoneFields(String[] fieldValues, Telephone telephone, Field field, String fieldName, int i) throws IllegalAccessException {
        field.setAccessible(true);
        if (fieldName.equals("price")) {
            field.set(telephone, new BigDecimal(fieldValues[i]));
        } else if (fieldName.equals("model")) {
            field.set(telephone, new Model(fieldValues[i]));
        } else {
            field.set(telephone, fieldValues[i]);
        }
    }

    private void setTelevisionFields(String[] fieldValues, Television television, Field field, String fieldName, int i) throws IllegalAccessException {
        field.setAccessible(true);
        if (fieldName.equals("diagonal")) {
            field.set(television, Integer.parseInt(fieldValues[i]));
        } else if (fieldName.equals("price")) {
            field.set(television, new BigDecimal(fieldValues[i]));
        } else {
            field.set(television, fieldValues[i]);
        }
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

    private InvoiceType getInvoiceType(Map<Product, Integer> products, BigDecimal limit) {
        BigDecimal summaryPrice = calculateTotalPriceForInvoice(products);
        if (summaryPrice.compareTo(limit) > 0) {
            return InvoiceType.WHOLESALE;
        }
        return InvoiceType.RETAIL;

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
