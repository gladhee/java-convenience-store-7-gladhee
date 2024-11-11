package store.repository.interfaces;

import java.util.List;
import store.domain.promotion.Promotion;
import store.domain.store.StoreProduct;

public interface StoreProductsRepository {
    void save(String name, StoreProduct storeProduct);

    StoreProduct findByName(String productName);

    List<StoreProduct> findAll();

}
