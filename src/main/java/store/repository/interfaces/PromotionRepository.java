package store.repository.interfaces;

import java.util.Map;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

public interface PromotionRepository {
    void save(String name, Promotion promotion);
    Promotion findByName(String promotionName);
    Map<String, Promotion> findAll();
}
