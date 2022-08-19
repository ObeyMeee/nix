package ua.com.andromeda.module2.entity;

public class ProductFactory {

    public Product createProduct(ProductType productType) {
        return switch (productType) {
            case TELEPHONE -> new Telephone();
            case TELEVISION -> new Television();
        };
    }
}
