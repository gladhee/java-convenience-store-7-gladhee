package store.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.promotion.Promotion;
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
        StoreProduct storeProduct = storeProducts.get(productName);
        if (storeProduct == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다.");
        }

        return storeProduct;
    }

    @Override
    public List<StoreProduct> findAll() {
        return new ArrayList<>(storeProducts.values());
    }



}
