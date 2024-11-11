package store.repository.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import store.domain.product.Product;
import store.exception.ProductException.DoesNotExistProductException;
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
            throw new DoesNotExistProductException();
        }

        return product;
    }

    @Override
    public Map<String, Product> findAll() {
        return products;
    }

}
