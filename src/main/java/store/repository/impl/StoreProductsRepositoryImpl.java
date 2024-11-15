package store.repository.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.store.StoreProduct;
import store.repository.interfaces.StoreProductsRepository;

public class StoreProductsRepositoryImpl implements StoreProductsRepository {

    private static StoreProductsRepositoryImpl instance;
    private final Map<String, StoreProduct> storeProducts = new LinkedHashMap<>();

    public static StoreProductsRepositoryImpl getInstance() {
        if (instance == null) {
            return new StoreProductsRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(String name, StoreProduct storeProduct) {
        storeProducts.put(name, storeProduct);
    }

    @Override
    public StoreProduct findByName(String productName) {
        return storeProducts.get(productName);
    }

    @Override
    public List<StoreProduct> findAll() {
        return new ArrayList<>(storeProducts.values());
    }



}
