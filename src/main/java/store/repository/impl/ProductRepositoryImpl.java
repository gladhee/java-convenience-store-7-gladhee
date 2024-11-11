package store.repository.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import store.domain.product.Product;
import store.repository.interfaces.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

    private static ProductRepositoryImpl instance;
    private final Map<String, Product> products = new LinkedHashMap<>();

    public static ProductRepositoryImpl getInstance() {
        if (instance == null) {
            return new ProductRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(String name, Product product) {
        products.put(name, product);
    }

    @Override
    public Product findByName(String productName) {
        Product product = products.get(productName);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다.");
        }

        return product;
    }

    @Override
    public Map<String, Product> findAll() {
        return products;
    }

}
