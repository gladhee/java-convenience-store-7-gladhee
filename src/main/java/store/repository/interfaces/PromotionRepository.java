package store.repository.interfaces;

import java.util.List;
import store.domain.promotion.Promotion;

public interface PromotionRepository {
    void save(Promotion promotion);
    Promotion findByName(String promotionName);
    List<Promotion> findAll();
}
