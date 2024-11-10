package store.repository.interfaces;

import java.util.Map;
import store.domain.product.Product;

public interface ProductRepository {
    void save(String name, Product product);
    Product findByName(String productName);
    Map<String, Product> findAll();
}
