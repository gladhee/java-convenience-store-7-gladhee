package store.repository.interfaces;

import java.util.Map;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

public interface PromotionRepository {
    public void save(String name, Promotion promotion);
    Promotion findByName(String promotionName);
    public Map<String, Promotion> findAll();
}
