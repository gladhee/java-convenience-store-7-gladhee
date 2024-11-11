package store.repository.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import store.domain.promotion.Promotion;
import store.repository.interfaces.PromotionRepository;

public class PromotionRepositoryImpl implements PromotionRepository {

    private static PromotionRepositoryImpl instance;
    private final Map<String, Promotion> promotions = new LinkedHashMap<>();

    public static PromotionRepositoryImpl getInstance() {
        if (instance == null) {
            return new PromotionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(String name, Promotion promotion) {
        promotions.put(name, promotion);
    }

    @Override
    public Promotion findByName(String promotionName) {
        Promotion promotion = promotions.get(promotionName);
        if (promotion == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션입니다.");
        }

        return promotion;
    }

    @Override
    public Map<String, Promotion> findAll() {
        return promotions;
    }

}